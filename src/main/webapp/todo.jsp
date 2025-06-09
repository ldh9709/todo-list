<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>할일 목록</title>
  <link rel="stylesheet" href="/static/css/common.css" />
</head>
<body>
<div class="container">
  <!-- 헤더 -->
  <header class="card">
    <div style="display: flex; justify-content: space-between; align-items: center;">
      <div>
        <h2 class="text-xl font-semibold">TodoList</h2>
        <span class="text-sm text-gray-600">안녕하세요, ${user.usersName}님!</span>
      </div>
      <div style="display: flex; gap: 8px;">
        <a href="/category" class="btn-outline">카테고리 관리</a>
        <a href="/logout" class="btn-outline">로그아웃</a>
      </div>
    </div>
  </header>

  <!-- 할일 추가 -->
  <section class="card">
    <div class="card-header">
      <div class="card-title">+ 새 할일 추가</div>
    </div>
    <form action="/todo/create" method="post">
      <label for="todoTitle">제목</label>
      <input type="text" id="todoTitle" name="todoTitle" class="input" placeholder="할일 제목을 입력하세요" required />

      <label for="todoContent">내용</label>
      <textarea id="todoContent" name="todoContent" class="input" placeholder="할일 내용을 입력하세요"></textarea>

      <label for="categoryNo">카테고리</label>
      <select name="categoryNo" id="categoryNo" class="input" required>
        <option value="">카테고리를 선택하세요</option>
        <c:forEach var="category" items="${categories}">
          <option value="${category.categoryNo}">${category.categoryName}</option>
        </c:forEach>
      </select>

      <button type="submit" class="btn w-full">할일 추가</button>
    </form>
  </section>

  <!-- 할일 목록 -->
  <section class="card">
    <div class="card-header" style="display: flex; justify-content: space-between; align-items: center;">
      <div class="card-title">할일 목록</div>
      <form method="get" action="/todo/filter">
        <select name="category" class="input">
          <option value="all">전체 카테고리</option>
          <c:forEach var="category" items="${categories}">
            <option value="${category.categoryNo}">${category.categoryName}</option>
          </c:forEach>
        </select>
      </form>
    </div>
    <div>
      <c:choose>
        <c:when test="${empty todos}">
          <div class="text-center py-8 text-gray-500">등록된 할일이 없습니다.</div>
        </c:when>
        <c:otherwise>
          <c:forEach var="todo" items="${todos}">
            <div class="card" style="margin-top: 16px;">
              <div style="display: flex; justify-content: space-between;">
                <div style="display: flex; gap: 12px;">
                  <form action="/todo/toggle" method="post">
                    <input type="hidden" name="todoNo" value="${todo.todoNo}" />
                    <input type="checkbox" class="checkbox" name="completed" onchange="this.form.submit()" <c:if test="${todo.todoCompleted}">checked</c:if> />
                  </form>
                  <div>
                    <div style="display: flex; align-items: center; gap: 8px;">
                      <strong class="${todo.todoCompleted ? 'line-through text-gray-500' : 'text-gray-900'}">
                        ${todo.todoTitle}
                      </strong>
                      <span class="badge">${todo.categoryName}</span>
                    </div>
                    <p class="text-sm ${todo.todoCompleted ? 'line-through text-gray-400' : 'text-gray-600'}">
                      ${todo.todoContent}
                    </p>
                    <p class="text-xs text-gray-400 mt-2">
                      생성일: ${todo.todoCreatedAt}
                    </p>
                  </div>
                </div>
                <form action="/todo/delete" method="post">
                  <input type="hidden" name="todoNo" value="${todo.todoNo}" />
                  <button type="submit" class="btn-outline">삭제</button>
                </form>
              </div>
            </div>
          </c:forEach>
        </c:otherwise>
      </c:choose>
    </div>
  </section>
</div>
</body>
</html>
