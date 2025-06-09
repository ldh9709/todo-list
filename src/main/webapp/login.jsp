<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>로그인</title>
  <link rel="stylesheet" href="/static/css/common.css" />
</head>
<body>
<div class="container">
  <h1 class="text-3xl font-bold text-gray-900">TodoList</h1>
  <p class="text-gray-600 mt-2">할 일 관리 시스템</p>

  <div class="tabs">
    <input type="radio" id="tab-login" name="tab" checked>
    <label for="tab-login">로그인</label>
    <input type="radio" id="tab-register" name="tab">
    <label for="tab-register">회원가입</label>

    <div class="tab-content" id="login">
      <div class="card">
        <div class="card-header">
          <div class="card-title">로그인</div>
          <p class="text-sm text-gray-500">계정에 로그인하세요</p>
        </div>
        <form action="/login" method="post">
          <label for="loginId">아이디</label>
          <input id="loginId" type="text" name="usersId" class="input" placeholder="아이디를 입력하세요" required>

          <label for="loginPassword">비밀번호</label>
          <input id="loginPassword" type="password" name="usersPassword" class="input" placeholder="비밀번호를 입력하세요" required>

          <button type="submit" class="btn w-full">로그인</button>
        </form>
      </div>
    </div>

    <div class="tab-content" id="register">
      <div class="card">
        <div class="card-header">
          <div class="card-title">회원가입</div>
          <p class="text-sm text-gray-500">새 계정을 만드세요</p>
        </div>
        <form action="/register" method="post">
          <label for="registerId">아이디</label>
          <input id="registerId" type="text" name="usersId" class="input" placeholder="아이디를 입력하세요" required>

          <label for="registerName">이름</label>
          <input id="registerName" type="text" name="usersName" class="input" placeholder="이름을 입력하세요" required>

          <label for="registerPassword">비밀번호</label>
          <input id="registerPassword" type="password" name="usersPassword" class="input" placeholder="비밀번호를 입력하세요" required>

          <button type="submit" class="btn w-full">회원가입</button>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
