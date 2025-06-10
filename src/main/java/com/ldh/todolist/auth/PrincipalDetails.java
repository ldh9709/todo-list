package com.ldh.todolist.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ldh.todolist.dto.UsersDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter	//Getter 자동 생성
@ToString	//각 객체의 필드값을 문자열로 출력하는 ToString 자동 생성
@EqualsAndHashCode	//두 객체가 같은지 비교할 수 있도록 equals()와 hashCode() 메서드를 자동 생성
public class PrincipalDetails implements UserDetails{
	
	// 로그인한 사용자의 정보를 담는 DTO (아이디, 비밀번호, 권한 등)
	private final UsersDto usersDto;
	
	// 로그인 시점에 해당 사용자의 정보를 전달받아 초기화하는 생성자
	public PrincipalDetails(UsersDto usersDto) {
		this.usersDto = usersDto;
	}
	
	
	//현재 로그인한 사용자의 권한(ROLE)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(usersDto.getUsersRole().name()));
	}
	
	//로그인할 때 사용할 비밀번호를 usersPassword로 설정
	@Override
	public String getPassword() {
		System.out.println("usersDto.getUsersPassword() : " + usersDto.getUsersPassword());
		return usersDto.getUsersPassword();
	}
	
	//로그인할 때 사용할 아이디를 usersId 설정
	@Override
	public String getUsername() {
		System.out.println("usersDto.getUsersId() : " + usersDto.getUsersId());
		return usersDto.getUsersId();
	}
	
	//사용자의 고유 번호 설정
	public Long getUsersNo() {
		
		return usersDto.getUsersNo();
	}
	
	//JWT 토큰을 생성하는데 필요한 사용자 정보
	public Map<String, Object> getClaims() {
		Map<String, Object> dataMap = new HashMap<>();
		
		dataMap.put("usersNo", usersDto.getUsersNo());
		dataMap.put("usersId", usersDto.getUsersId());
		dataMap.put("usersName", usersDto.getUsersName());
		dataMap.put("usersRole", usersDto.getUsersRole().name());//Enum 문자열로 반환
		
		return dataMap;
	}


	/*
	 * 계정 만료 여부
	 * true : 만료 안됨
	 * false : 만료됨
	 */
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/*
	 * 계정 잠김 여부
	 * true : 잠기지 않음
	 * false : 잠김
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/*
	 * 비밀번호 만료 여부
	 * true : 만료 안됨
	 * false : 만료됨
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/*
	 * 계정 활성화 여부
	 * true : 활성화됨
	 * false : 활성화 안됨
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
