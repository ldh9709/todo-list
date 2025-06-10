package com.ldh.todolist.service;

import com.ldh.todolist.dto.UsersDto;

public interface UsersService {
	
	//회원가입
	void saveUser(UsersDto usersDto);
	
	//회원수정
	void updateUser(UsersDto usersDto);

	//회원탈퇴
	void deleteUser(Long usersNo);
	
	//NO로 회원조회
	UsersDto findByUsersNo(Long usersNo);
	
	//ID로 회원조회
	UsersDto findById(String usersId);

}
