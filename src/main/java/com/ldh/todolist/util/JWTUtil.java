package com.ldh.todolist.util;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import com.ldh.todolist.exception.CustomJWTException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {

    //JWT 서명용 비밀 키
    private static final String key = "384398573895734298com.ldh.todolist984120921";

    //JWT 토큰 생성 메서드
    public static String generateToken(Map<String, Object> valueMap, int min) {

        SecretKey key = null;

        try {
            //시크릿 문자열을 바이트 배열로 변환한 후 HMAC-SHA256용 SecretKey 생성
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        } catch (Exception e) {
        	
            //키 생성 실패 시 런타임 예외 발생
            throw new RuntimeException(e.getMessage());
        }

        //JWT 토큰 생성: 헤더, 클레임, 발급일, 만료일, 서명 포함
        String jwtStr = Jwts.builder()
        		//헤더에 typ: JWT 명시
                .setHeader(Map.of("typ", "JWT"))
                //사용자 정의 클레임 설정 (ex: 사용자 정보)
                .setClaims(valueMap)
                //발급 시간
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                //만료 시간 설정
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(120).toInstant())) 
                //서명 키 설정
                .signWith(key) 
                //최종 JWT 문자열 생성
                .compact(); 

        return jwtStr;
    }

    /* —————————————————— JWT 토큰 유효성 검증 메서드 —————————————————— */
    public static Map<String, Object> validateToken(String token) {

        Map<String, Object> claim = null;

        try {
            //시크릿 키 재생성 (토큰 발급 시 사용한 키와 동일하게 생성해야 함)
            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));

            //토큰 파싱 및 서명 검증
            claim = Jwts.parserBuilder()
            		//서명 검증용 키 설정
                    .setSigningKey(key) 
                    .build()
                    //서명 검증 포함된 JWT 파싱
                    .parseClaimsJws(token) 
                    //유효한 경우 클레임 반환
                    .getBody(); 

        } catch (MalformedJwtException malformedJwtException) {
            // 토큰 구조가 잘못된 경우
            throw new CustomJWTException("MalFormed");
        } catch (ExpiredJwtException expiredJwtException) {
            // 토큰이 만료된 경우
            throw new CustomJWTException("Expired");
        } catch (InvalidClaimException invalidClaimException) {
            // 클레임 값이 유효하지 않을 경우
            throw new CustomJWTException("Invalid");
        } catch (JwtException jwtException) {
            // 기타 JWT 관련 에러 (예: 서명 오류 등)
            throw new CustomJWTException("JWTError");
        } catch (Exception e) {
            // 예기치 못한 모든 예외 처리
            throw new CustomJWTException("Error");
        }

        return claim; // 유효한 토큰의 클레임 반환
    }

}
