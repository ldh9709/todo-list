package com.ldh.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.todolist.dao.UsersDao;
import com.ldh.todolist.dto.UsersDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
	
	private final UsersDao usersDao;
	
	@Override
	public void saveUser(UsersDto usersDto) {
		usersDao.insert(usersDto);
	}

	@Override
	public void updateUser(UsersDto usersDto) {
		usersDao.update(usersDto);
	}

	@Override
	public void deleteUser(int usersNo) {
		usersDao.delete(usersNo);
	}

	@Override
	public UsersDto findById(int usersNo) {
		
		UsersDto findUser = usersDao.findById(usersNo);
		
		return findUser;
	}
	
}
