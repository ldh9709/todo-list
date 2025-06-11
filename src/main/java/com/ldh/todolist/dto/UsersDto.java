package com.ldh.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private Long usersNo;         // 사용자 고유번호 (PK)
    private String usersId;      // 사용자 아이디
    private String usersPassword;// 비밀번호
    private String usersName;    // 이름
    private Role usersRole;    // 역할(유저)
 
}

