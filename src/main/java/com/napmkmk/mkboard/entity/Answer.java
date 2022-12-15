package com.napmkmk.mkboard.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Answer {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE)
	private Integer id; //답변게시판번호
	
	@Column(length = 1000)
	private String content; //답변 게시판 내용
	
	private LocalDateTime createTime;
	
	@ManyToOne //n:1구조(질문1개에 답변 여러개가 달리는 구조- 부모(질문) 자식(답변)) 
	private Question question; //질문게시판 객체(질문게시판의 id(외래키) 를 가져오는 필드가 생성됨) ㅁ 연결되어 있는 다른키의 이름은 외래키
	
	@ManyToOne
	private SiteMember writer;//글쓴이
	
	private LocalDateTime modifyDate; // 수정한 데이터
	
	@ManyToMany //다대다 관계일 때 새로운 answer_liker테이블이 생성되고 , 필드값은 각 테이블의 기본키가 된다.
	private Set<SiteMember> liker; //좋아요 누른 아이디 (Set집합 중복을 허락하지 않아요.)
	
}
