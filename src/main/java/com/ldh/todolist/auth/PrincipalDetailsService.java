package com.ldh.todolist.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ldh.todolist.dao.UsersDao;
import com.ldh.todolist.dto.UsersDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor	//UsersRepository 생성자 주입
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UsersDao usersDao;
	
	// 사용자 ID로 DB에서 사용자 정보를 조회하고, UserDetails 구현체로 반환
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("username : " + username);
		
		//아이디로 사용자 조회, 없으면 예외 처리
		UsersDto findUsers = usersDao.findById(username);
		
		System.out.println("findUsers : " + findUsers);
		return new PrincipalDetails(findUsers);
	}
	
}
