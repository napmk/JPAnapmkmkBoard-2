package com.napmkmk.mkboard.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE) //IDENTITY 테이블별로 시퀀스 만들기
	private Integer id; //질문게시판번호
	
	@Column(length = 100)
	private String subject; //게시판 제목
	
	@Column(length = 1000)
	private String content; //게시판 내용
	
	private LocalDateTime createDate; // 글 등록일시 
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) //Answer의 private Question question; 뒤에 가져옴
	private List<Answer> answerList;//1:n구조 java.util.List
}
