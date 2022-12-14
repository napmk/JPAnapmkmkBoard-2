package com.napmkmk.mkboard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.napmkmk.mkboard.dto.QuestionDto;
import com.napmkmk.mkboard.entity.Answer;
import com.napmkmk.mkboard.entity.Question;
import com.napmkmk.mkboard.exception.DataNotFoundException;
import com.napmkmk.mkboard.repository.AnswerRepository;
import com.napmkmk.mkboard.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	
	public Page<Question> getList(int page){
		
		List<Sort.Order> sort = new ArrayList<>();
		
		sort.add(Sort.Order.desc("id"));
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sort)); //페이지당 표시되는 글 개수
		
		Page<Question> pages = questionRepository.findAll(pageable);
		return pages;
	}
	
	
	public List<QuestionDto> getQuestionList() {
		List<Question> questionList = questionRepository.findAll();
		
		
		List<QuestionDto> questionDtos = new ArrayList<QuestionDto>();
		
		//수작업으로 매핑함.
		for( int i=0;i<questionList.size();i++) {
			Question question = questionList.get(i);
			QuestionDto questionDto = new QuestionDto();
			questionDto.setId(question.getId());
			questionDto.setContent(question.getContent());
			questionDto.setSubject(question.getSubject());
			questionDto.setAnswers(question.getAnswerList());
			questionDto.setCreateDate(question.getCreateDate());
			
			questionDtos.add(questionDto);
		}
		
	
		return questionDtos;
		
		
	}
	
	//글 클릭당 글 하나보기
	 public QuestionDto getQuestion(Integer id) {
		 
		 QuestionDto questionDto = new QuestionDto();
		 
		 Optional<Question> optquestion = questionRepository.findById(id);
		 if(optquestion.isPresent()) {
			Question question = optquestion.get();
			
			questionDto.setId(question.getId());
			questionDto.setContent(question.getContent());
			questionDto.setSubject(question.getSubject());
			questionDto.setAnswers(question.getAnswerList());
			questionDto.setCreateDate(question.getCreateDate());
			
			return questionDto;
		 }else{
			 throw new DataNotFoundException("해당 질문이 없습니다.");
			 
		 }


		 
	 }
	 
	 public void questionCreate(String subject, String content) {
		 
		 
		// List<Question> optOptional = questionRepository.findBySubjectAndContent(subject, content);
 
		 Question question2 = new Question();
		 question2.setContent(content);
		 question2.setSubject(subject);
		 question2.setCreateDate(LocalDateTime.now());
		 
	
		 questionRepository.save(question2);
		
	 }

}
