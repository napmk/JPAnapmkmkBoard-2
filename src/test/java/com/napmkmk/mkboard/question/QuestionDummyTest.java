package com.napmkmk.mkboard.question;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.napmkmk.mkboard.service.QuestionService;

@SpringBootTest
public class QuestionDummyTest {

	@Autowired
	private QuestionService questionService;
	
	@Test
	public void testDummyData() {
		for(int i=0;i<150;i++) { // 150개 더미데이터 만들기
			String subject = String.format("테스트 데이터 입니다:%d", i);
			String content = "테스트 테이터 내용입니다. 내용은 없습니다.";
			 
			questionService.questionCreate(subject, content, "apple");
		}
	}
}
