package com.ldh.todolist.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersDto {
    private Long usersNo;         // 사용자 고유번호 (PK)
    private String usersId;      // 사용자 아이디
    private String usersPassword;// 비밀번호
    private String usersName;    // 이름
    private Role usersRole;    // 역할(유저)
 
    public void getUserPassword () {
    	System.out.println(usersPassword);
    }
}

