package com.ldh.todolist.service;

import java.util.List;

import com.ldh.todolist.dto.CategoryDto;

public interface CategoryService {
	
	//카테고리 추가
	void saveCategory(CategoryDto categoryDto);
	
	//카테고리 수정
	void updateCategory(CategoryDto categoryDto);

	//카테고리 삭제
	void deleteCategory(int categoryNo);
	
	//카테고리 조회
	CategoryDto findById(int categoryNo);
	
	//카테고리 목록 조회
	List<CategoryDto> findByUsersNo(int usersNo);

}
