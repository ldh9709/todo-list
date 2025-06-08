package com.ldh.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ldh.todolist.dao.TodoDao;
import com.ldh.todolist.dto.TodoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
	
	private final TodoDao todoDao;
	
	@Override
	public void saveTodo(TodoDto todoDto) {
		todoDao.insert(todoDto);
	}

	@Override
	public void updateTodo(TodoDto todoDto) {
		todoDao.update(todoDto);
	}

	@Override
	public void deleteTodo(int todoNo) {
		todoDao.delete(todoNo);
	}

	@Override
	public TodoDto findById(int todoNo) {
		
		TodoDto findTodo = todoDao.findById(todoNo);
		
		return findTodo;
	}

	@Override
	public List<TodoDto> findByUsersNo(int usersNo) {
		
		List<TodoDto> findTodoList = todoDao.findByUsersNo(usersNo);
		
		return findTodoList;
	}
	
}
