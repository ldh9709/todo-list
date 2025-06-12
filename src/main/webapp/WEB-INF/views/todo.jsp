<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>TodoList - 할일 관리</title>
  <link rel="stylesheet" href="/css/htmlstyle.css" />
</head>

<body>
  <!-- ===== 헤더 ===== -->
  <header class="header">
    <div class="header-content">
      <div class="header-left">
        <h1>📝 TodoList</h1>
        <span class="user-info">
          안녕하세요, <sec:authentication property="name" />님!
        </span>
      </div>
      <div class="header-right">
        <a href="category.html" class="btn btn-outline">카테고리 관리</a>
        <a href="login.html" class="btn btn-outline">로그아웃</a>
      </div>
    </div>
  </header>

  <!-- ===== 본문 ===== -->
  <div class="main-container">
    <div class="content-grid">

      <!-- 새 할일 추가 -->
      <aside class="sidebar">
        <div class="card">
          <div class="card-header"><h3>➕ 새 할일 추가</h3></div>
          <div class="card-body">
            <form id="todoForm" action="<c:url value='/todo' />" method="post">
              <div class="form-group">
                <label for="todoTitle">제목</label>
                <input id="todoTitle" name="todoTitle" type="text"
                       placeholder="할일 제목을 입력하세요" required />
              </div>

              <div class="form-group">
                <label for="todoContent">내용</label>
                <textarea id="todoContent" name="todoContent" rows="3"
                          placeholder="할일 내용을 입력하세요"></textarea>
              </div>

              <div class="form-group">
                <label for="categoryNo">카테고리</label>
                <select id="categoryNo" name="categoryNo" required>
                  <option value="">카테고리를 선택하세요</option>
                  <c:forEach var="category" items="${categoryList}">
                    <option value="${category.categoryNo}">
                      ${category.categoryName}
                    </option>
                  </c:forEach>
                </select>
              </div>

              <button type="submit" class="btn btn-primary">할일 추가</button>
            </form>
          </div>
        </div>
      </aside>

      <!-- 할일 목록 -->
      <section class="main-content">
        <div class="card">
          <div class="card-header">
            <h3>📋 할일 목록</h3>
            <div class="filter-section">
              <select id="categoryFilter" onchange="filterTodos()">
                <option value="all">전체 카테고리</option>
                <c:forEach var="category" items="${categoryList}">
                  <option value="${category.categoryNo}">
                    ${category.categoryName}
                  </option>
                </c:forEach>
              </select>
            </div>
          </div>

          <div class="card-body">
            <div class="todo-list">
              <c:forEach var="todo" items="${todoList}">
                <div class="todo-item" data-category="${todo.categoryNo}">
                  <div class="todo-content">

                    <!-- 체크박스 -->
                    <div class="todo-checkbox">
                      <input type="checkbox"
                             id="todo${todo.todoNo}"
                             name="todo-completed"
                             value="${todo.todoCompleted}"
                             onchange="toggleTodo(${todo.todoNo})"
                             <c:if test="${todo.todoCompleted}">checked</c:if> />
                      <label for="todo${todo.todoNo}"></label>
                    </div>

                    <!-- 제목·내용 -->
                    <div class="todo-details">
                      <div class="todo-header">
                        <h4 contenteditable="false"
                            onblur="saveTodo(this, ${todo.todoNo})">
                          ${todo.todoTitle}
                        </h4>
                        <span class="category-badge category-${todo.categoryNo}">
                          <c:forEach var="category" items="${categoryList}">
                            <c:if test="${category.categoryNo == todo.categoryNo}">
                              ${category.categoryName}
                            </c:if>
                          </c:forEach>
                        </span>
                      </div>
                      <p class="todo-description">${todo.todoContent}</p>
                      <p class="todo-date">생성일 : ${todo.todoCreatedAt}</p>
                    </div>
                  </div>

                  <!-- 액션 버튼 -->
                  <div class="todo-actions">
                    <button class="btn-icon" onclick="editTodo(${todo.todoNo})">✏️</button>
                    <!-- 삭제 폼(hidden) -->
                    <form id="deleteForm${todo.todoNo}" method="post" action="/todo/${todo.todoNo}/delete">
                      <input type="hidden" name="usersNo" value="${todo.usersNo}" />
                      <button class="btn-icon" onclick="deleteTodo(${todo.todoNo})" title="삭제">🗑️</button>
                    </form>
                  </div>


                </div>
              </c:forEach>
            </div>
          </div>
        </div>
      </section>

    </div>
  </div>

  <!-- ===== 스크립트 ===== -->
  <script>
    // 완료 체크 토글
    function toggleTodo(todoNo) {
      const checkbox = document.querySelector('#todo' + todoNo);
      const todoItem = checkbox.closest('.todo-item');
      todoItem.classList.toggle('completed');

      fetch('/todo/' + todoNo + '/completed/update', { method: 'POST' })
        .catch(err => {
          todoItem.classList.toggle('completed'); // 롤백
          console.error(err);
        });
    }

    // 카테고리 필터
    function filterTodos() {
      const filter = document.getElementById('categoryFilter').value;
      document.querySelectorAll('.todo-item').forEach(item => {
        item.style.display =
          filter === 'all' || item.dataset.category === filter ? 'flex' : 'none';
      });
    }

    // 수정 이동
    function editTodo(todoNo) {
      window.location.href = "/todo/" + todoNo + "/detail";
    };

    // 삭제 이동
    function deleteTodo(todoNo) {
      if (confirm("정말 삭제하시겠습니까?")) {
        document.getElementById("deleteForm" + todoNo).submit();
        }
    };
  </script>
</body>
</html>
