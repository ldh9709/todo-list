package com.ldh.todolist.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoDto {
    private int todoNo;          // 할 일 번호 (PK)
    private String todoTitle;  // 할 일 제목
    private String todoContent;  // 할 일 내용
    private boolean todoCompleted; // 완료 여부 (TINYINT → boolean 매핑)
    private LocalDateTime todoCreatedAt; // 생성일 (DATETIME)
    private int usersNo;         // 사용자 번호 (FK)
    private int categoryNo;         // 카테고리 번호 (FK)
}
