package com.napmkmk.mkboard.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.napmkmk.mkboard.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	public List<Question> findBySubject(String subject); //결과가 여러개 일수 있으므로 List
	//제목과 정확히 모두 일치하는 글찾기
	

	
	public List<Question>findBySubjectLike(String subject);
	 //제목에 특정 낱말이 포함되어 있는 글 찾기
	
	
	public List<Question> findBySubjectOrderByIdDesc(String subject); 
	//제목과 정확히 모두 일치하는 글찾은후 아이디 내림차순으로 정렬하여 반환


//	public List<Question> findBySubjectAndContent(String subject, String content);
//	//제목과 컨텐츠 입력하기
	
	public Page<Question> findAll(Pageable pageable);

}
