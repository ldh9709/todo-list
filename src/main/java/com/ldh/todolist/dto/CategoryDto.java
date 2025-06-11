package com.ldh.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long categoryNo;          // 카테고리 번호 (PK)
    private Long usersNo;  // 사용자 고유번호 (FK)
    private String categoryName;  // 카테고리 이름
}
