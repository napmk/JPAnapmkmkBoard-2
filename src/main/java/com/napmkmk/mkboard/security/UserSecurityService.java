package com.napmkmk.mkboard.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.napmkmk.mkboard.entity.SiteMember;
import com.napmkmk.mkboard.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<SiteMember> optSiteMember = memberRepository.findByUsername(username);
		
		if(optSiteMember.isEmpty()) { //사이트 맴버에서 확인했는데 아무것도 없다!! 참이면 입된 아이디가 없다는것
			throw new UsernameNotFoundException("등록되지 않은 아이디입니다.");
		}
		
		SiteMember siteMember =optSiteMember.get();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		
		return new User(siteMember.getUsername(), siteMember.getPassword(), authorities);
	}

	@Bean
	   AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	      return authenticationConfiguration.getAuthenticationManager();
	   }
}
