package com.napmkmk.mkboard.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.napmkmk.mkboard.entity.Answer;
import com.napmkmk.mkboard.entity.Question;
import com.napmkmk.mkboard.repository.AnswerRepository;
import com.napmkmk.mkboard.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	private final QuestionRepository questionRepository;
	
	public void answerCreate(String content, Integer Id) {//tntity 보다 dto로 가져오면 더 좋음
		Optional<Question> optOptional = questionRepository.findById(Id);
		Question question = optOptional.get();
		
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateTime(LocalDateTime.now());
		answer.setQuestion(question);
		
		answerRepository.save(answer);
	}
}
