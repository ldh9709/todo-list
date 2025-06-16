<!-- UTF-8로 페이지 인코딩 -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- JSTL태그 라이브러리 선언 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Spring Security 라이브러리 선언 -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>카테고리 수정</title>
    <link rel="stylesheet" href="/css/htmlstyle.css" />
  </head>
  <body>
    <div class="main-container">
      <div class="card">
        <div class="card-header">
          <h3>✏️ 카테고리 수정</h3>
        </div>
        <div class="card-body">
          <form action="/category/${category.categoryNo}/update" method="post">
            <div class="form-group">
              <label for="categoryName">카테고리 이름</label>
              <input
                type="text"
                id="categoryName"
                name="categoryName"
                value="${category.categoryName}"
                required
              />
            </div>
            <div
              class="form-group"
              style="display: flex; justify-content: flex-end; gap: 10px"
            >
              <a href="/category" class="btn btn-outline">❌</a>
              <button type="submit" class="btn btn-primary">💾</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </body>
</html>
