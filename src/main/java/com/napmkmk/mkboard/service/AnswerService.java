package com.napmkmk.mkboard.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.napmkmk.mkboard.entity.Answer;
import com.napmkmk.mkboard.entity.Question;
import com.napmkmk.mkboard.entity.SiteMember;
import com.napmkmk.mkboard.exception.DataNotFoundException;
import com.napmkmk.mkboard.repository.AnswerRepository;
import com.napmkmk.mkboard.repository.MemberRepository;
import com.napmkmk.mkboard.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	private final QuestionRepository questionRepository;
	private final MemberService memberService;
	
	public void answerCreate(String content, Integer Id, String username) {//tntity 보다 dto로 가져오면 더 좋음
		Optional<Question> optOptional = questionRepository.findById(Id);
		Question question = optOptional.get();
		
		SiteMember siteMember = memberService.getMemberInfo(username);
		
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateTime(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setWriter(siteMember);
		
		answerRepository.save(answer);
	}
	
	public Answer getAnswer(Integer id) {
		Optional<Answer> optAnswer = answerRepository.findById(id);
		
		if(optAnswer.isPresent()) {
			return optAnswer.get();
		} else {
			throw new DataNotFoundException("해당 답변이 없습니다.");
		}
	}
	
	 public void answerModify( String content, Answer answer) {
			
		 answer.setContent(content);
		 answer.setModifyDate(LocalDateTime.now());
		 answerRepository.save(answer);
        
	 }
	 
	 public void answerDelete(Integer Id) {
		 
		 answerRepository.deleteById(Id);
		 
	 }
	 
	 public void answerLike(Answer answer, SiteMember siteMember) {
		 answer.getLiker().add(siteMember);
		 //현재 질문글이 가지고 있는 좋아요를 누른 회원의 집합을 가져온 후 그 집합에 새로운 좋아요 클릭 회원 객체를 추가
		 answerRepository.save(answer);
	
		 
	 }
	 
}
