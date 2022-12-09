package com.napmkmk.mkboard.question;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.napmkmk.mkboard.entity.Answer;
import com.napmkmk.mkboard.entity.Question;
import com.napmkmk.mkboard.repository.AnswerRepository;
import com.napmkmk.mkboard.repository.QuestionRepository;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")

public class AnswerTest {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
//	@Test
//	@DisplayName("답변생성테스트")
//	public void testAnswer1() {
//		
//		Optional<Question> q2 = questionRepository.findById(2);
//		Question qq =  q2.get();
//		
//		Answer aa = new Answer();
//		
//		aa.setContent("답변입니다");
//		aa.setCreateTime(LocalDateTime.now());
//		aa.setQuestion(qq);//답변이 달릴 질문 글 객체 셋팅
//		
//		answerRepository.save(aa);
//	}
	
	@Test
	@DisplayName("답변조회테스트1")
	public void testAnswer2() {
	 Optional<Answer> a1 = answerRepository.findById(9);  //아이디9번글을 찾아줘라 
	 
	 Answer aa = a1.get(); //a1찾아서 aa 에 Answer 에 넣어라
	 
	 assertEquals("답변입니다", aa.getContent());
	 
	}
	
	@Test
	@javax.transaction.Transactional
	@DisplayName("질문에 달린 답변조회 테스트")
	public void testAnswer3() {
		Optional<Question> q2 = questionRepository.findById(2);
    	Question qq =  q2.get();
    	
    	List<Answer> answerList = qq.getAnswerList();
    	
    	assertEquals(1, answerList.size());
    	assertEquals("답변입니다.", answerList.get(0).getContent());
	}
}
