package com.ldh.todolist.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

//로그인 실패 시 실행되는 핸들러 클래스 (API 전용 응답 처리)
@Log4j2
public class APILoginFailHandler implements AuthenticationFailureHandler {

    //로그인 실패 시 호출되는 메서드
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        
        //로그인 실패 로그 출력
    	String username = request.getParameter("username");
    	String ip = request.getRemoteAddr();

    	log.warn("로그인 실패 - 아이디: {} / 사유: {} / IP: {} / UA: {}",
    	         username, exception.getClass().getSimpleName(), ip);
    	response.sendRedirect("/login.jsp?error=true");
    }
}
