package com.napmkmk.mkboard.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.napmkmk.mkboard.entity.SiteMember;
import com.napmkmk.mkboard.exception.DataNotFoundException;
import com.napmkmk.mkboard.repository.MemberRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteMember  memberCreate(String username, String password, String email) {
		
		
		SiteMember member = new SiteMember();
		member.setUsername(username);
		//member.setPassword(password);//해시알고리즘으로 처리해줘야함.
	//	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setPassword(passwordEncoder.encode(password));
		
		member.setEmail(email);
		
		memberRepository.save(member);
		
		return member;
		
	}
	
	public SiteMember getMemberInfo(String username) {
		Optional<SiteMember> optSiteMember =  memberRepository.findByUsername(username);
		
		if(optSiteMember.isPresent()) {
			SiteMember siteMember = optSiteMember.get();
			return siteMember;
		}else {
			throw new DataNotFoundException("유저를 찾을 수 없습니다.");
		}
	}
	
}
