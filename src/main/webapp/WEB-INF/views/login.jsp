<!-- UTF-8로 페이지 인코딩 -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- JSTL태그 라이브러리 선언 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>TodoList - 로그인</title>
    <link rel="stylesheet" href="/css/htmlstyle.css" />
  </head>
  <body>
    <div class="login-container">
      <div class="login-box">
        <div class="logo">
          <h1>📝 TodoList</h1>
          <p>할 일 관리 시스템</p>
        </div>

        <div class="tab-container">
          <div class="tab-buttons">
            <button class="tab-btn active" onclick="showTab('login')">
              로그인
            </button>
            <button class="tab-btn" onclick="showTab('register')">
              회원가입
            </button>
          </div>

          <!-- 로그인 폼 -->
          <div id="login-tab" class="tab-content active">
            <form
              class="auth-form"
              action="<c:url value='/login' />"
              method="post"
            >
              <c:if test="${param.error == 'true'}">
                <p style="color: red; margin-top: 10px">
                  아이디 혹은 비밀번호를 다시 확인해주세요.
                </p>
              </c:if>
              <div class="form-group">
                <label for="loginId">아이디</label>
                <input
                  type="text"
                  id="usersId"
                  name="usersId"
                  placeholder="아이디를 입력하세요"
                  required
                />
              </div>
              <div class="form-group">
                <label for="loginPassword">비밀번호</label>
                <input
                  type="password"
                  id="usersPassword"
                  name="usersPassword"
                  placeholder="비밀번호를 입력하세요"
                  required
                />
              </div>
              <button type="submit" class="btn btn-primary">로그인</button>
            </form>
          </div>

          <!-- 회원가입 폼 -->
          <div id="register-tab" class="tab-content">
            <form
              class="auth-form"
              action="<c:url value='/users/register' />"
              method="post"
            >
              <div class="form-group">
                <label for="registerId">아이디</label>
                <input
                  type="text"
                  id="registerId"
                  name="usersId"
                  placeholder="아이디를 입력하세요"
                  required
                />
              </div>
              <div class="form-group">
                <label for="registerName">이름</label>
                <input
                  type="text"
                  id="registerName"
                  name="usersName"
                  placeholder="이름을 입력하세요"
                  required
                />
              </div>
              <div class="form-group">
                <label for="registerPassword">비밀번호</label>
                <input
                  type="password"
                  id="registerPassword"
                  name="usersPassword"
                  placeholder="비밀번호를 입력하세요"
                  required
                />
              </div>
              <button type="submit" class="btn btn-primary">회원가입</button>
            </form>
          </div>
        </div>
      </div>
    </div>

    <script>
      function showTab(tabName) {
        // 모든 탭 버튼과 컨텐츠에서 active 클래스 제거
        document
          .querySelectorAll(".tab-btn")
          .forEach((btn) => btn.classList.remove("active"));
        document
          .querySelectorAll(".tab-content")
          .forEach((content) => content.classList.remove("active"));

        // 선택된 탭 버튼과 컨텐츠에 active 클래스 추가
        event.target.classList.add("active");
        document.getElementById(tabName + "-tab").classList.add("active");
      }
    </script>
  </body>
</html>
