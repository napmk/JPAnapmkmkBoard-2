package com.napmkmk.mkboard.security;


import lombok.Getter;

@Getter
public enum UserRole { //enum 열거형

		ADMIN("ROLE ADMIN"),
		USER("ROLE_USER");
	
	UserRole(String value){
		this.value = value;
	}
	
	private String value;
}
