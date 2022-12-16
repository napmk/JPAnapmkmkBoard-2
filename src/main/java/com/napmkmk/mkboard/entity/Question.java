package com.napmkmk.mkboard.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
	
	
	@ManyToOne
	private SiteMember writer;//글쓴이
	
	private LocalDateTime modifyDate; // 수정한 데이터
	
	@ManyToMany //다대다 관계일 때 새로운 qustion_liker테이블이 생성되고 , 필드값은 각 테이블의 기본키가 된다.
	private Set<SiteMember> liker; //좋아요 누른 아이디 (Set집합 중복을 허락하지 않아요.)
	//set의 원소의 개수가 곧 좋아요의 수가 됨
}
