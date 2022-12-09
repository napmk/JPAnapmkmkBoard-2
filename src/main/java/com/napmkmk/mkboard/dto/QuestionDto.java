package com.napmkmk.mkboard.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.napmkmk.mkboard.entity.Answer;

import lombok.Data;

@Data
public class QuestionDto {
	private Integer id;
	private String subject;
	private String content;
	private LocalDateTime createDate;
	
	private List<Answer> answers;
	
}
