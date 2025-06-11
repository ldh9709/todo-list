package com.ldh.todolist.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;

import com.ldh.todolist.auth.PrincipalDetails;
import com.ldh.todolist.dto.Role;
import com.ldh.todolist.dto.UsersDto;
import com.ldh.todolist.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * 매 요청마다 JWT 토큰을 확인하고 인증 정보를 SecurityContext에 설정하는 필터 JWT가 있는 경우 토큰을 파싱해 사용자
 * 정보를 SecurityContext에 저장 이후 필터, 컨트롤러에서 인증된 사용자로 동작 가능
 */
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

	// 이 필터를 적용하지 않을 요청 정의
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		// Preflight(사전 요청)은 필터 적용 제외 (CORS 관련)
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		}

		String path = request.getRequestURI();
		log.info("check uri.............." + path);
		log.info("shouldNotFilter path = " + path);

		// 특정 경로는 JWT 검사 없이 통과시키기
		if (path.startsWith("/swagger-ui") || path.startsWith("/login") || path.startsWith("/**")
				|| path.startsWith("/favicon.ico") || path.startsWith("/v3/api-docs") || path.startsWith("/main")
				/* || path.startsWith("/users") */
				|| path.startsWith("/logout")) {
			return true;
		}

		// 그 외의 요청은 필터 적용
		return false;
	}

	// 실제 JWT 검사 및 인증 설정 로직
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 1. "users" 쿠키에서 Base64 인코딩된 JSON 문자열 추출
		String encodedJson = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("users".equals(cookie.getName())) {
					encodedJson = cookie.getValue();
					break;
				}
			}
		}

		// 2. 쿠키가 없으면 다음 필터로 넘어감
		if (encodedJson == null) {
			filterChain.doFilter(request, response);
			return;
		}

		try {

			// 3. Base64 디코딩 후 JSON 문자열로 변환
			String jsonStr = new String(Base64.getDecoder().decode(encodedJson));

			// 4. Gson으로 JSON 파싱 → accessToken 꺼냄
			Gson gson = new Gson();
			Map<String, String> tokenMap = gson.fromJson(jsonStr, Map.class);
			String accessToken = tokenMap.get("accessToken");

			if (accessToken == null || accessToken.isBlank()) {
				filterChain.doFilter(request, response);
				return;
			}

			// 5. 토큰 유효성 검사 및 클레임 추출
			Map<String, Object> claims = JWTUtil.validateToken(accessToken);
			log.info("JWT claims: {}", claims);

			// 6. 사용자 정보 추출
			Long usersNo = ((Number) claims.get("usersNo")).longValue();
			String usersId = (String) claims.get("usersId");
			String roleName = (String) claims.get("usersRole");
			Role usersRole = Role.valueOf(roleName);

			// 7. DTO → PrincipalDetails → 인증 객체 생성
			UsersDto usersDto = UsersDto.builder().usersNo(usersNo).usersName(usersId).usersRole(usersRole).build();

			PrincipalDetails principalDetails = new PrincipalDetails(usersDto);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					principalDetails, null, principalDetails.getAuthorities());

			// 8. SecurityContext에 인증 객체 설정
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		} catch (Exception e) {
			// 9. 토큰 검증 실패 → 401 에러 응답
			log.error("JWT 검증 실패: {}", e.getMessage());
			e.printStackTrace();

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json;charset=UTF-8");

			String msg = new Gson().toJson(Map.of("errorCode", "EXPIRED_TOKEN", "message", "토큰이 만료되었거나 유효하지 않습니다"));
			PrintWriter printWriter = response.getWriter();
			printWriter.println(msg);
			printWriter.close();
			return;

		}
	    // 10. 필터 체인 계속 진행
	    filterChain.doFilter(request, response);
	}
}
