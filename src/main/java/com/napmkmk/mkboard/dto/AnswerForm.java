package com.napmkmk.mkboard.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AnswerForm {

	@NotEmpty(message = "답변은 필수 입력사항입니다!")
	@Size(min = 20, message = "답변 내용은 10자 이상만 가능합니다.") //20byte를 안되면 에러
	private String content;
}
