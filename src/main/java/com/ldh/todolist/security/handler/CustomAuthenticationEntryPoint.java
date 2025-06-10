package com.ldh.todolist.security.handler;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* TOKEN 만료 시 커스텀 에러 전송 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		//401 에러
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
		
        // 메시지와 코드 함께 전달
        response.getWriter().write(
            new ObjectMapper().writeValueAsString(Map.of(
                "errorCode", "EXPIRED_TOKEN",
                "message", "토큰이 만료되었거나 유효하지 않습니다"
            ))
        );
	}

	
	
}
