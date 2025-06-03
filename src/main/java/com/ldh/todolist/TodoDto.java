package com.ldh.todolist;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TodoDto {
    private int todoNo;          // 할 일 번호 (PK)
    private String todoContent;  // 할 일 내용
    private boolean todoCompleted; // 완료 여부 (TINYINT → boolean 매핑)
    private LocalDateTime todoCreatedAt; // 생성일 (DATETIME)
    private int usersNo;         // 사용자 번호 (FK)
}
