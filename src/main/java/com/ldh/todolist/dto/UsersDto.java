package com.ldh.todolist.dto;

import lombok.Data;

@Data
public class UsersDto {
    private int usersNo;         // 사용자 고유번호 (PK)
    private String usersId;      // 사용자 아이디
    private String usersPassword;// 비밀번호
    private String usersName;    // 이름
}
