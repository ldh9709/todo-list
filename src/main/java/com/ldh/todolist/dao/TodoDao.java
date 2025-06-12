package com.ldh.todolist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ldh.todolist.dto.TodoDto;

//투두리스트
@Mapper
public interface TodoDao {
	
	//할 일 작성
	void insert(TodoDto todoDto); 
	
	//할 일 수정
	void update(TodoDto todoDto);
	
	//할 일 완료 여부 수정
	void updateTodoCompleted(TodoDto todoDto);

	//할 일 삭제
	void delete(Long todoNo);

	//할 일 조회
	TodoDto findById(Long todoNo);
	
	//할 일 목록 조회(사용자 번호)
	List<TodoDto> findByUsersNo(Long usersNo);
	
}
