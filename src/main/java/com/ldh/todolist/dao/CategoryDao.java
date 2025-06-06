package com.ldh.todolist.dao;

import java.util.List;

import com.ldh.todolist.dto.CategoryDto;

//카테고리
public interface CategoryDao {
	
	//카테고리 생성
	void insert(CategoryDto categoryDto); 
	
	//카테고리 수정
	void update(CategoryDto categoryDto);

	//카테고리 삭제
	void delete(int categoryNo);
	
	//카테고리 조회
	CategoryDto findById(int categoryNo);
	
	//카테고리 리스트 조회
	List<CategoryDto> findAll();
	
}
