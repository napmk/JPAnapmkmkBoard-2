package com.napmkmk.mkboard.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

//validation
@Data
public class QuestionForm {
	
	@NotEmpty(message = "제목은 필수 입력사항입니다!")
	@Size(max = 200, message = "제목은 100자 이하만 가능합니다.") //200byte를 넘기면 에러
	private String subject;
	
	@NotEmpty(message = "내용은 필수 입력사항입니다!")
	@Size(min = 20, message = "내용은 10자 이상만 가능합니다.") //20byte를 안되면 에러
	private String content;
}
