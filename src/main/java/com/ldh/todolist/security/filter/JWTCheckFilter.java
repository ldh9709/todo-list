package com.ldh.todolist.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * 매 요청마다 JWT 토큰을 확인하고 인증 정보를 SecurityContext에 설정하는 필터
 * JWT가 있는 경우 토큰을 파싱해 사용자 정보를 SecurityContext에 저장
 * 이후 필터, 컨트롤러에서 인증된 사용자로 동작 가능
 */
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    //이 필터를 적용하지 않을 요청 정의
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        //Preflight(사전 요청)은 필터 적용 제외 (CORS 관련)
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String path = request.getRequestURI();
        log.info("check uri.............." + path);
        log.info("shouldNotFilter path = " + path);

        //특정 경로는 JWT 검사 없이 통과시키기
        if (path.startsWith("/swagger-ui")
        		|| path.startsWith("/login")
        		|| path.startsWith("/**")
                || path.startsWith("/favicon.ico")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/main")
                /* || path.startsWith("/users") */
                || path.startsWith("/logout")) {
            return true;
        }

        //그 외의 요청은 필터 적용
        return false;
    }

    //실제 JWT 검사 및 인증 설정 로직
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //1. 요청 헤더에서 Authorization 추출
        String authHeaderStr = request.getHeader("Authorization");

        //2. 토큰이 없거나 형식이 "Bearer "로 시작하지 않으면 필터 통과 (인증 X)
        if (authHeaderStr == null || !authHeaderStr.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            //3. 실제 토큰 문자열 추출 (Bearer 제외)
            String accessToken = authHeaderStr.substring(7);

            //4. 토큰 유효성 검사 및 클레임 추출
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);
            log.info("JWT claims: " + claims);

            //5. 클레임에서 사용자 정보 추출
            Long usersNo = ((Number) claims.get("usersNo")).longValue(); // JSON에서 숫자는 Number로 받음
            String usersId = (String) claims.get("usersId");
            String roleName = (String) claims.get("usersRole");
            Role usersRole = Role.valueOf(roleName); // 문자열을 enum으로 변환

            //6. 사용자 정보를 담은 DTO 생성
            UsersDto usersDto = UsersDto.builder()
                    .usersNo(usersNo)
                    .usersName(usersId) // ID를 이름 필드에 넣음 (사용자 이름으로 쓰려면 따로 수정 필요)
                    .usersRole(usersRole)
                    .build();

            //7. UserDetails(PrincipalDetails) 생성
            PrincipalDetails principalDetails = new PrincipalDetails(usersDto);

            //8. 인증 객체 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            //9. 인증 정보를 SecurityContext에 설정
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            //10. 필터 체인 계속 진행
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            //토큰 검증 실패 시 예외 처리
            e.printStackTrace();
            log.error("JWT Check Error..............");
            log.error(e.getMessage());

            //에러 응답 JSON 생성 및 전송
            Gson gson = new Gson();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            String msg = gson.toJson(Map.of("errorCode", "EXPIRED_TOKEN", "message", "토큰이 만료되었거나 유효하지 않습니다"));
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
            
            return;
        }
    }
}
