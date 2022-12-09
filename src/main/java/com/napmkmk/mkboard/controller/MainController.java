package com.napmkmk.mkboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.napmkmk.mkboard.dto.QuestionDto;
import com.napmkmk.mkboard.entity.Question;
import com.napmkmk.mkboard.repository.AnswerRepository;
import com.napmkmk.mkboard.repository.QuestionRepository;
import com.napmkmk.mkboard.service.AnswerService;
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
	public String list(Model model) {
		
		//List<Question> questionList = questionRepository.findAll(); 서비스 사용하려고 주석처리
		List<QuestionDto> questionList = questionService.getQuestionList();
	
		model.addAttribute("questionList",questionList);
		
		return "question_List";
	}
	
	//리스트 뷰페이지
	@RequestMapping( value = "/questionView/{id}") //타임리프 방법
	public String questionView(Model model, @PathVariable("id")Integer id) { //글번호 메개변수 id로 들어 온다
		
		QuestionDto question= questionService.getQuestion(id);
		model.addAttribute("question", question);
		
		return "question_View";
	}
	
	@RequestMapping(value = "/answerCreate/{id}")
	public String answerCreate(@PathVariable("id")Integer id, @RequestParam String content) { //번호 랑 리퀘스트파람 가져오기 //27?와 content=+답변등록
		
		answerService.answerCreate(content, id);
		
		
		return String.format("redirect:/questionView/%s", id); //파라미터값넘겨주기
	}
	
}
