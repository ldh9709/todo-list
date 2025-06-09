package com.ldh.todolist.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ldh.todolist.dto.UsersDto;

//사용자
@Mapper
public interface UsersDao {
	
	//회원가입
	void insert(UsersDto usersDto); 
	
	//사용자 정보 수정
	void update(UsersDto usersDto);

	//회원탈퇴
	void delete(int usersNo);
	
	//사용자 조회
	UsersDto findById(int usersNo);
	
}
