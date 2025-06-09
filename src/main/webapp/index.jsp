<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <title>JSP To-Do List</title>
    <link rel="stylesheet" href="/css/style.css" />
  </head>
  <body>
    <div class="page-wrapper">
      <div class="content-container">
        <!-- 헤더 영역 -->
        <div class="header-card">
          <div class="header-flex">
            <div>
              <h1 class="header-title">JSP To-Do List Manager</h1>
              <p class="header-subtitle">
                Java Server Pages 스타일 할일 관리 시스템
              </p>
            </div>
            <div class="header-time">
              <div class="time-label">현재 시간</div>
              <div class="time-value">
                <%= new java.util.Date().toLocaleString() %>
              </div>
            </div>
          </div>
        </div>

        <!-- 통계 카드 -->
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-value text-blue">0</div>
            <div class="stat-label">전체 할일</div>
          </div>

          <div class="stat-card">
            <div class="stat-value text-green">0</div>
            <div class="stat-label">완료된 할일</div>
          </div>

          <div class="stat-card">
            <div class="stat-value text-orange">0</div>
            <div class="stat-label">남은 할일</div>
          </div>
        </div>

        <!-- 할일 추가 폼 -->
        <div class="form-card">
          <div class="form-header">
            <div class="form-title">새 할일 추가</div>
          </div>
          <form action="/todo/create" method="post">
            <div class="form-row">
              <input
                type="text"
                name="text"
                class="todo-input"
                placeholder="새로운 할일을 입력하세요..."
                required
              />
              <button type="submit" class="btn btn-save">추가</button>
            </div>
          </form>
        </div>

        <!-- 할일 목록 -->
        <div class="list-card">
          <div class="list-header">
            <div class="list-title">할일 목록</div>
          </div>
          <div class="divider"></div>

          <c:forEach var="todo" items="${todoList}" varStatus="status">
            <div class="todo-row ${todo.completed ? 'completed' : ''}">
              <div class="todo-number">#${status.index + 1}</div>
              <input type="checkbox" ${todo.completed ? 'checked' : ''}
              onclick="location.href='/todo/toggle?id=${todo.id}'" />
              <div class="todo-content">
                <label class="todo-label ${todo.completed ? 'completed' : ''}"
                  >${todo.text}</label
                >
                <div class="todo-date">생성일: ${todo.createdAt}</div>
              </div>
              <div class="todo-actions">
                <form action="/todo/edit" method="get" style="display: inline">
                  <input type="hidden" name="id" value="${todo.id}" />
                  <button type="submit" class="btn btn-edit">수정</button>
                </form>
                <form
                  action="/todo/delete"
                  method="post"
                  style="display: inline"
                >
                  <input type="hidden" name="id" value="${todo.id}" />
                  <button type="submit" class="btn btn-delete">삭제</button>
                </form>
              </div>
            </div>
          </c:forEach>
        </div>

        <!-- 푸터 -->
        <div class="footer">
          <div>JSP To-Do List Manager v1.0</div>
          <div class="footer-powered">
            Powered by Java Server Pages Architecture
          </div>
          <div class="footer-copy">
            © 2024 JSP Todo Application. All rights reserved.
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
