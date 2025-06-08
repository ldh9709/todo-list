package com.ldh.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ldh.todolist.dao.CategoryDao;
import com.ldh.todolist.dto.CategoryDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryDao categorysDao;
	
	@Override
	public void saveCategory(CategoryDto categorysDto) {
		categorysDao.insert(categorysDto);
	}

	@Override
	public void updateCategory(CategoryDto categorysDto) {
		categorysDao.update(categorysDto);
	}

	@Override
	public void deleteCategory(int categorysNo) {
		categorysDao.delete(categorysNo);
	}

	@Override
	public CategoryDto findById(int categorysNo) {
		
		CategoryDto findCategory = categorysDao.findById(categorysNo);
		
		return findCategory;
	}

	@Override
	public List<CategoryDto> findByUsersNo(int usersNo) {
		
		List<CategoryDto> findCategoryList = categorysDao.findByUsersNo(usersNo);
		
		return findCategoryList;
	}
	
}
