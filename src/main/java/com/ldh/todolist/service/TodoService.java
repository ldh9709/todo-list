package com.ldh.todolist.service;

import java.util.List;

import com.ldh.todolist.dto.TodoDto;

public interface TodoService {
	
	//할 일 추가
	void saveTodo(TodoDto todoDto);
	
	//할 일 수정
	void updateTodo(TodoDto todoDto);

	//할 일 삭제
	void deleteTodo(int todoNo);
	
	//할 일 조회
	TodoDto findById(int todoNo);
	
	//할 일 목록 조회(사용자 번호)
	List<TodoDto> findByUsersNo(int usersNo);
}
