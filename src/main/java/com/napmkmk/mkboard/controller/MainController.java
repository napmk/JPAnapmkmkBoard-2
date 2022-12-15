package com.napmkmk.mkboard.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.napmkmk.mkboard.dto.AnswerForm;
import com.napmkmk.mkboard.dto.MemberForm;
import com.napmkmk.mkboard.dto.QuestionDto;
import com.napmkmk.mkboard.dto.QuestionForm;
import com.napmkmk.mkboard.entity.Answer;
import com.napmkmk.mkboard.entity.Question;
import com.napmkmk.mkboard.repository.AnswerRepository;
import com.napmkmk.mkboard.repository.QuestionRepository;
import com.napmkmk.mkboard.service.AnswerService;
import com.napmkmk.mkboard.service.MemberService;
import com.napmkmk.mkboard.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //방법1 롬복 소속 - > 레퍼지토리 부르기
@Controller
public class MainController {
//방법1	
//	private final QuestionRepository questionRepository;
//	private final AnswerRepository answerRepository;

// 방법2 레퍼지토리 부르기
//	@Autowired    
//	private QuestionRepository questionRepository;
//	
//	@Autowired
//	private AnswerRepository answerRepository;
// 방법2 레퍼지토리 부르기
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final MemberService memberService;
	
	@RequestMapping(value = "/")
//	@ResponseBody //본문에 아래 들어가 있는 텍스트 출력됨
	public String home() {
		
		return "redirect:list";
	}
	
	
	@RequestMapping(value = "/index")
	public String index() {
		
		 
		
		return "redirect:list";
	}
	//리스트페이지
	@RequestMapping(value = "/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) { //스프링부트는 페이징 첫페이지 0
		
		//List<Question> questionList = questionRepository.findAll(); 서비스 사용하려고 주석처리
		//List<QuestionDto> questionList = questionService.getQuestionList();
	
		Page<Question> paging = questionService.getList(page);// 페이지를 만들어서 넣어주자
		
		model.addAttribute("pageCount",paging.getTotalElements());//전체 게시물 개수
		model.addAttribute("paging",paging);
		
		return "question_List";
	}
	
	//리스트 뷰페이지
	@RequestMapping( value = "/questionView/{id}") //타임리프 방법
	public String questionView(Model model, @PathVariable("id")Integer id , AnswerForm answerForm) { //글번호 메개변수 id로 들어 온다
		
		Question question= questionService.getQuestion(id);
		model.addAttribute("question", question);
		
		return "question_View";
	}
	
	//답변페이지
	@PreAuthorize("isAuthenticated")//로그아웃했을때 글 남기면 오류처리 막아줌
	@PostMapping(value = "/answerCreate/{id}")
	public String answerCreate(Model model,@PathVariable("id")Integer id, 
			@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal ) { //번호 랑 리퀘스트파람 가져오기 //27?와 content=+답변등록  principal 현재로그인사용자 가져오기
		
		Question  question = questionService.getQuestion(id); //원글의 내용
		
		
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question" ,question);  //원글가져와라.
			return "question_view"; //원글을 다시받고 에러도 찍고.
		}
		answerService.answerCreate(answerForm.getContent(), id , principal.getName());
		
		
		return String.format("redirect:/questionView/%s", id); //파라미터값넘겨주기 에러가 전달이 안되므로
	}
	
	@PreAuthorize("isAuthenticated")//로그아웃했을때 글 남기면 오류처리 막아줌
	@GetMapping(value = "/questionCreate")
	public String questionCreate (QuestionForm questionForm){ 
		
		return "question_form"; //파라미터값넘겨주기
	}
	
	@PreAuthorize("isAuthenticated")//로그아웃했을때 글 남기면 오류처리 막아줌
	@PostMapping(value = "/questionCreate") //postMapping  post일때 사용하기
	public String questionCreateOk (@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal){ //QuestionForm안(subject, content)에 있는 벨리데이션을 체크 걸리면에러발생 
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		questionService.questionCreate(questionForm.getSubject(), questionForm.getContent(), principal.getName());  
		
		
		return "redirect:list"; 
	}
	
	@RequestMapping(value = "/join")
	public String join(MemberForm memberForm){ 
		
		return "join_form"; 
	}
	
	
	@PostMapping(value = "/joinOk") //postMapping  post일때 사용하기
	public String joinOk (@Valid MemberForm memberForm, BindingResult bindingResult){ //MemberForm안(subject, content)에 있는 벨리데이션을 체크 걸리면에러발생 
		
		
		
		
		if(bindingResult.hasErrors()) {
			return "join_form";
		}
		
		try {
		memberService.memberCreate(memberForm.getUsername(), memberForm.getPassword(), memberForm.getEmail());  
		}catch(Exception e){   //아이디 가입 에러 사유
			e.printStackTrace();
			bindingResult.reject("joinFail","이미 등록된 아이디 입니다.");
			return "join_form";
		}
		return "redirect:list"; 
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		
		return "login_form";
	}
	
	@PreAuthorize("isAuthenticated")//로그아웃했을때 글 남기면 오류처리 막아줌
	@RequestMapping(value = "/modify/{id}")
	public String modify(@PathVariable("id") Integer Id, QuestionForm questionForm, Principal principal) {
		
		Question question = questionService.getQuestion(Id);
		
		if(!question.getWriter().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		}
		
		
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated")//로그아웃했을때 글 남기면 오류처리 막아줌
	@PostMapping( value = "/modify/{id}")
	public String questionModify(@PathVariable("id") Integer Id,@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		Question question = questionService.getQuestion(Id);
		
		questionService.modify(questionForm.getSubject(), questionForm.getContent(), question);
		return String.format("redirect:/questionView/%s", Id); 
	}
	
	@PreAuthorize("isAuthenticated")//로그아웃했을때 글 남기면 오류처리 막아줌
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") Integer Id, QuestionForm questionForm, Principal principal) {
		
		Question question = questionService.getQuestion(Id);
		
		if(!question.getWriter().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다");
		}
		
		
		questionService.delete(Id);
		
		
		
		return "redirect:/index"; 
	}
	
	
	@PreAuthorize("isAuthenticated")//로그아웃했을때 글 남기면 오류처리 막아줌
	@RequestMapping(value = "/answerModify/{id}")
	public String answerModify(@PathVariable("id") Integer Id, AnswerForm answerForm, Principal principal) {
		
		
		Answer answer = answerService.getAnswer(Id);
		
		
		if(!answer.getWriter().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		}
		
	
		return "answer_form";
	}
	
	@PreAuthorize("isAuthenticated")//로그아웃했을때 글 남기면 오류처리 막아줌
	@PostMapping( value = "/answerModify/{id}")
	public String answerModify(@PathVariable("id") Integer Id,@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "answer_form";
		}
		
		Answer answer = answerService.getAnswer(Id);
		
		if(!answer.getWriter().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		}
		
		answerService.answerModify(answerForm.getContent(), answer);
		
		return String.format("redirect:/questionView/%s", answer.getQuestion().getId()); 
	}
	
	@PreAuthorize("isAuthenticated")//로그아웃했을때 글 남기면 오류처리 막아줌
	@RequestMapping(value = "/answerDelete/{id}")
	public String answerDelete(@PathVariable("id") Integer Id,  Principal principal) {
		
		Answer answer = answerService.getAnswer(Id);
		
		if(!answer.getWriter().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다");
		}
		
		
		answerService.answerDelete(Id);
		
		
		
		return String.format("redirect:/questionView/%s", answer.getQuestion().getId()); 
	}
	
}
