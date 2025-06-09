<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>카테고리 관리</title>
  <link rel="stylesheet" href="/static/css/common.css" />
</head>
<body>
<div class="container">
  <!-- 헤더 -->
  <header class="card">
    <div style="display: flex; justify-content: space-between; align-items: center;">
      <div>
        <a href="/todo" class="btn-outline">← 할일 목록으로</a>
        <h2 class="text-xl font-semibold">카테고리 관리</h2>
      </div>
      <div class="text-sm text-gray-600">${user.usersName}님의 카테고리</div>
    </div>
  </header>

  <!-- 새 카테고리 추가 -->
  <section class="card">
    <div class="card-header">
      <div class="card-title">+ 새 카테고리 추가</div>
    </div>
    <form action="/category/create" method="post">
      <label for="categoryName">카테고리 이름</label>
      <input type="text" id="categoryName" name="categoryName" class="input" required />
      <button type="submit" class="btn w-full">카테고리 추가</button>
    </form>
  </section>

  <!-- 카테고리 목록 -->
  <section class="card">
    <div class="card-header">
      <div class="card-title">카테고리 목록</div>
    </div>
    <c:forEach var="category" items="${categories}">
      <div class="card" style="margin-bottom: 12px; display: flex; justify-content: space-between; align-items: center;">
        <form action="/category/edit" method="post" style="flex: 1; display: flex; gap: 8px; align-items: center;">
          <input type="hidden" name="categoryNo" value="${category.categoryNo}" />
          <span class="badge">#${category.categoryNo}</span>
          <input type="text" name="categoryName" value="${category.categoryName}" class="input" style="flex: 1;" />
          <button type="submit" class="btn">저장</button>
        </form>
        <form action="/category/delete" method="post">
          <input type="hidden" name="categoryNo" value="${category.categoryNo}" />
          <button type="submit" class="btn-outline">삭제</button>
        </form>
      </div>
    </c:forEach>
  </section>

  <!-- 통계 -->
  <section class="card">
    <div class="card-header">
      <div class="card-title">카테고리 통계</div>
    </div>
    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 16px;">
      <div class="card" style="background-color: #eff6ff; text-align: center;">
        <div class="text-2xl font-bold text-blue-600">${categories.size()}</div>
        <div class="text-sm text-blue-600">총 카테고리 수</div>
      </div>
      <div class="card" style="background-color: #ecfdf5; text-align: center;">
        <div class="text-2xl font-bold text-green-600">3</div>
        <div class="text-sm text-green-600">활성 카테고리</div>
      </div>
      <div class="card" style="background-color: #f5f3ff; text-align: center;">
        <div class="text-2xl font-bold text-purple-600">12</div>
        <div class="text-sm text-purple-600">총 할일 수</div>
      </div>
    </div>
  </section>
</div>
</body>
</html>
