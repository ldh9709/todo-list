package com.ldh.todolist.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private int categoryNo;          // 카테고리 번호 (PK)
    private int usersNo;  // 사용자 고유번호 (FK)
    private String categoryName;  // 카테고리 이름
}
