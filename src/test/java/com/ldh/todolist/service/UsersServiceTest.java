package com.ldh.todolist.service;

import com.ldh.todolist.dao.UsersDao;
import com.ldh.todolist.dto.UsersDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsersServiceTest {

    @Autowired
    private UsersDao usersDao;

    @Test
    void 아이디로_사용자_조회_성공() {
  
        String userId = "qwe123";

        
        UsersDto user = usersDao.findById(userId);

        // then
        assertThat(user).isNotNull();
        assertThat(user.getUsersId()).isEqualTo("qwe123");
    }
}
