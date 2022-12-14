package com.napmkmk.mkboard.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.napmkmk.mkboard.dto.AnswerForm;
import com.napmkmk.mkboard.dto.MemberForm;
import com.napmkmk.mkboard.dto.QuestionDto;
import com.napmkmk.mkboard.dto.QuestionForm;
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
		
		model.addAttribute("paging",paging);
		
		return "question_List";
	}
	
	//리스트 뷰페이지
	@RequestMapping( value = "/questionView/{id}") //타임리프 방법
	public String questionView(Model model, @PathVariable("id")Integer id , AnswerForm answerForm) { //글번호 메개변수 id로 들어 온다
		
		QuestionDto question= questionService.getQuestion(id);
		model.addAttribute("question", question);
		
		return "question_View";
	}
	
	//답변페이지
	@PostMapping(value = "/answerCreate/{id}")
	public String answerCreate(Model model,@PathVariable("id")Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult ) { //번호 랑 리퀘스트파람 가져오기 //27?와 content=+답변등록
		
		QuestionDto questionDto = questionService.getQuestion(id); //원글의 내용
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question" ,questionDto);  //원글가져와라.
			return "question_view"; //원글을 다시받고 에러도 찍고.
		}
		answerService.answerCreate(answerForm.getContent(), id);
		
		
		return String.format("redirect:/questionView/%s", id); //파라미터값넘겨주기 에러가 전달이 안되므로
	}
	
	@RequestMapping(value = "/question_form")
	public String questionCreate (QuestionForm questionForm){ 
		
		return "question_form"; //파라미터값넘겨주기
	}
	
	
	@PostMapping(value = "/questionCreate") //postMapping  post일때 사용하기
	public String questionCreateOk (@Valid QuestionForm questionForm, BindingResult bindingResult){ //QuestionForm안(subject, content)에 있는 벨리데이션을 체크 걸리면에러발생 
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		questionService.questionCreate(questionForm.getSubject(), questionForm.getContent());  
		
		
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
	
}
