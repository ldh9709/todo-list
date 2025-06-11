package com.ldh.todolist.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ldh.todolist.dao.UsersDao;
import com.ldh.todolist.dto.UsersDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

	private final UsersDao usersDao;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void saveUser(UsersDto usersDto) {
		String encodedPassword = passwordEncoder.encode(usersDto.getUsersPassword());
		
		usersDto.setUsersPassword(encodedPassword);
		
		usersDao.insert(usersDto);
	}

	@Override
	public void updateUser(UsersDto usersDto) {
		usersDao.update(usersDto);
	}

	@Override
	public void deleteUser(Long usersNo) {
		usersDao.delete(usersNo);
	}

	@Override
	public UsersDto findByUsersNo(Long usersNo) {

		UsersDto findUser = usersDao.findByUsersNo(usersNo);

		return findUser;
	}

	@Override
	public UsersDto findById(String usersId) {

		UsersDto findUser = usersDao.findById(usersId);

		return findUser;
	}

}
