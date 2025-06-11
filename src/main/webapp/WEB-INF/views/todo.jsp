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
    <title>TodoList - 할일 관리</title>
    <link rel="stylesheet" href="/css/htmlstyle.css" />
  </head>
  <body>
    <!-- 헤더 -->
    <header class="header">
      <div class="header-content">
        <div class="header-left">
          <h1>📝 TodoList</h1>
          <span class="user-info"
            >안녕하세요, <sec:authentication property="name" />님!</span
          >
        </div>
        <div class="header-right">
          <a href="category.html" class="btn btn-outline">카테고리 관리</a>
          <a href="login.html" class="btn btn-outline">로그아웃</a>
        </div>
      </div>
    </header>

    <div class="main-container">
      <div class="content-grid">
        <!-- 새 할일 추가 -->
        <div class="sidebar">
          <div class="card">
            <div class="card-header">
              <h3>➕ 새 할일 추가</h3>
            </div>
            <div class="card-body">
              <form
                id="todoForm"
                action="<c:url value='/todo' />"
                method="post"
              >
                <div class="form-group">
                  <label for="todoTitle">제목</label>
                  <input
                    type="text"
                    id="todoTitle"
                    name="todoTitle"
                    placeholder="할일 제목을 입력하세요"
                    required
                  />
                </div>
                <div class="form-group">
                  <label for="todoContent">내용</label>
                  <textarea
                    id="todoContent"
                    name="todoContent"
                    placeholder="할일 내용을 입력하세요"
                    rows="3"
                  ></textarea>
                </div>
                <!-- 카테고리 -->
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
        </div>

        <!-- 할일 목록 -->
        <div class="main-content">
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
                <!-- 할일 아이템 1 -->
                <c:forEach var="todo" items="${todoList}">
                  <div class="todo-item" data-category="${todo.categoryNo}">
                    <div class="todo-content">
                      <div class="todo-checkbox">
                        <input
                          type="checkbox"
                          id="todo${todo.todoNo}"
                          name="todo-completed"
                          value="${todo.todoCompleted}"
                          onchange="toggleTodo(1)"
                        />
                        <label for="todo${todo.todoNo}"></label>
                      </div>
                      <div class="todo-details">
                        <div class="todo-header">
                          <h4>${todo.todoTitle}</h4>
                          <span
                            class="category-badge category-${todo.categoryNo}"
                          >
                            <c:forEach var="category" items="${categoryList}">
                              <c:if
                                test="${category.categoryNo == todo.categoryNo}"
                              >
                                ${category.categoryName}
                              </c:if>
                            </c:forEach>
                          </span>
                        </div>
                        <p class="todo-description">${todo.todoContent}</p>
                        <p class="todo-date">생성일:${todo.todoCreatedAt}</p>
                      </div>
                    </div>
                    <div class="todo-actions">
                      <button
                        class="btn-icon"
                        onclick="editTodo(1)"
                        title="수정"
                      >
                        ✏️
                      </button>
                      <button
                        class="btn-icon"
                        onclick="deleteTodo(1)"
                        title="삭제"
                      >
                        🗑️
                      </button>
                    </div>
                  </div>
                </c:forEach>

                <!-- 할일 아이템 2 (완료됨) -->
                <div class="todo-item completed" data-category="2">
                  <div class="todo-content">
                    <div class="todo-checkbox">
                      <input
                        type="checkbox"
                        id="todo2"
                        checked
                        onchange="toggleTodo(2)"
                      />
                      <label for="todo2"></label>
                    </div>
                    <div class="todo-details">
                      <div class="todo-header">
                        <h4>프로젝트 회의 준비</h4>
                        <span class="category-badge category-2">업무</span>
                      </div>
                      <p class="todo-description">
                        다음 주 프로젝트 회의 자료 준비
                      </p>
                      <p class="todo-date">생성일: 2024-01-14 09:00</p>
                    </div>
                  </div>
                  <div class="todo-actions">
                    <button class="btn-icon" onclick="editTodo(2)" title="수정">
                      ✏️
                    </button>
                    <button
                      class="btn-icon"
                      onclick="deleteTodo(2)"
                      title="삭제"
                    >
                      🗑️
                    </button>
                  </div>
                </div>

                <!-- 할일 아이템 3 -->
                <div class="todo-item" data-category="1">
                  <div class="todo-content">
                    <div class="todo-checkbox">
                      <input
                        type="checkbox"
                        id="todo3"
                        onchange="toggleTodo(3)"
                      />
                      <label for="todo3"></label>
                    </div>
                    <div class="todo-details">
                      <div class="todo-header">
                        <h4>운동하기</h4>
                        <span class="category-badge category-1">개인</span>
                      </div>
                      <p class="todo-description">헬스장에서 1시간 운동</p>
                      <p class="todo-date">생성일: 2024-01-16 07:00</p>
                    </div>
                  </div>
                  <div class="todo-actions">
                    <button class="btn-icon" onclick="editTodo(3)" title="수정">
                      ✏️
                    </button>
                    <button
                      class="btn-icon"
                      onclick="deleteTodo(3)"
                      title="삭제"
                    >
                      🗑️
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script>
      function toggleTodo(todoNo) {
        const todoItem = document
          .querySelector(`#todo${todoNo}`)
          .closest(".todo-item");
        todoItem.classList.toggle("completed");

        // 실제 구현시에는 AJAX로 서버에 상태 업데이트 요청
        // updateTodoStatus(todoNo, isCompleted);
      }

      function filterTodos() {
        const filter = document.getElementById("categoryFilter").value;
        const todoItems = document.querySelectorAll(".todo-item");

        todoItems.forEach((item) => {
          if (filter === "all" || item.dataset.category === filter) {
            item.style.display = "flex";
          } else {
            item.style.display = "none";
          }
        });
      }

      function editTodo(todoNo) {
        alert(`할일 ${todoNo} 수정 기능 (실제로는 수정 폼으로 이동)`);
      }

      function deleteTodo(todoNo) {
        if (confirm("정말로 이 할일을 삭제하시겠습니까?")) {
          // 실제 구현시에는 서버로 삭제 요청
          alert(`할일 ${todoNo} 삭제됨`);
        }
      }
    </script>
  </body>
</html>
