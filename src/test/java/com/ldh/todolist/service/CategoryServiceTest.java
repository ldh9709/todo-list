package com.ldh.todolist.service;

import com.ldh.todolist.dao.CategoryDao;
import com.ldh.todolist.dao.UsersDao;
import com.ldh.todolist.dto.CategoryDto;
import com.ldh.todolist.dto.TodoDto;
import com.ldh.todolist.dto.UsersDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
public class CategoryServiceTest {


    @Autowired
    private CategoryDao categoryDao;
    

    @Test
    void No로_Category_조회_성공() {
  
        Long usersNo = 1L;
        
        List<CategoryDto> categoryList = categoryDao.findByUsersNo(usersNo);
        
        assertThat(categoryList).isNotNull(); // null이 반환되면 테스트 실패
        assertThat(categoryList).isNotEmpty(); // 데이터가 있어야 성공, 빈 데이터는 실패
        assertThat(categoryList.get(0).getUsersNo()).isEqualTo(usersNo); //첫 번째 할일의 사용자 번호가 테스트에 사용한 usersNo와 같아야 함
        
        System.out.println(categoryList);
        
    }
}
