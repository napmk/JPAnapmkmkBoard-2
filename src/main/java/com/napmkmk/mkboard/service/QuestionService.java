package com.napmkmk.mkboard.service;

import java.security.Principal;
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
import com.napmkmk.mkboard.entity.SiteMember;
import com.napmkmk.mkboard.exception.DataNotFoundException;
import com.napmkmk.mkboard.repository.AnswerRepository;
import com.napmkmk.mkboard.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	private final MemberService memberService;
	
	public Page<Question> getList(int page){
		
		List<Sort.Order> sort = new ArrayList<>();
		
		sort.add(Sort.Order.desc("id"));
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sort)); //페이지당 표시되는 글 개수
		
		Page<Question> pages = questionRepository.findAll(pageable);
		return pages;
	}
	
	
	public List<Question> getQuestionList() {
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
		
	
		return questionList;
		
		
	}
	
	//글 클릭당 글 하나보기
	 public Question getQuestion(Integer id) {
		 
		 //QuestionDto questionDto = new QuestionDto();
		 
		 Optional<Question> optquestion = questionRepository.findById(id);
		 if(optquestion.isPresent()) {
			Question question = optquestion.get();
			
//			questionDto.setId(question.getId());
//			questionDto.setContent(question.getContent());
//			questionDto.setSubject(question.getSubject());
//			questionDto.setAnswers(question.getAnswerList());
//			questionDto.setCreateDate(question.getCreateDate());
//			
			return question;
			
		 }else{
			 throw new DataNotFoundException("해당 질문이 없습니다.");
			 
		 }


		 
	 }
	 
	 public void questionCreate(String subject, String content ,String username) {
		 
		 
		// List<Question> optOptional = questionRepository.findBySubjectAndContent(subject, content);
		 
		 SiteMember siteMember = memberService.getMemberInfo(username);
		 
		 Question question = new Question();
		 question.setContent(content);
		 question.setSubject(subject);
		 question.setCreateDate(LocalDateTime.now());
		 question.setWriter(siteMember);
	
		 questionRepository.save(question);
		
	 }
	 
	 public void modify(String subject, String content, Question question) {
		
		 question.setSubject(subject);
         question.setContent(content);
         question.setModifyDate(LocalDateTime.now()); //수정시간을 지금 현재시간으로 셋팅
         
         questionRepository.save(question);
	 }
	 
	 public void delete(Integer Id) {
		 
		 questionRepository.deleteById(Id);
		 
	 }

	 public void questionLike(Question question, SiteMember siteMember) {
		 question.getLiker().add(siteMember);
		 //현재 질문글이 가지고 있는 좋아요를 누른 회원의 집합을 가져온 후 그 집합에 새로운 좋아요 클릭 회원 객체를 추가
		 questionRepository.save(question);
		 
	 }
}
