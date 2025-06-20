package com.ldh.todolist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ldh.todolist.dto.CategoryDto;

//카테고리
@Mapper
public interface CategoryDao {
	 
	//카테고리 생성
	void insert(CategoryDto categoryDto); 
	
	//카테고리 수정
	void update(CategoryDto categoryDto);

	//카테고리 삭제
	void delete(Long categoryNo);
	
	//카테고리 조회
	CategoryDto findById(Long categoryNo);
	
	//카테고리 리스트 조회
	List<CategoryDto> findByUsersNo(Long usersNo);
	
}
