package com.ldh.todolist.service;

import com.ldh.todolist.dao.TodoDao;
import com.ldh.todolist.dao.UsersDao;
import com.ldh.todolist.dto.TodoDto;
import com.ldh.todolist.dto.UsersDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
public class TodoServiceTest {


    @Autowired
    private TodoDao todoDao;
    

    @Test
    void 아이디로_Todo_조회_성공() {
  
        String userId = "qwe123";
        
        Long usersNo = 1L;
        
        List<TodoDto> todoList = todoDao.findByUsersNo(usersNo);
        
        assertThat(todoList).isNotNull(); // null이 반환되면 테스트 실패
        assertThat(todoList).isNotEmpty(); // 데이터가 있어야 성공, 빈 데이터는 실패
        assertThat(todoList.get(0).getUsersNo()).isEqualTo(usersNo); //첫 번째 할일의 사용자 번호가 테스트에 사용한 usersNo와 같아야 함
        
        System.out.println(todoList);
        
    }
}
