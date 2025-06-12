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
                          onchange="toggleTodo(${todo.todoNo})"
                          <c:if test="${todo.todoCompleted}">checked</c:if>
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
                        onclick="editTodo('${todo.todoNo}')"
                        title="수정"
                      >
                        ✏️
                      </button>
                      <button
                        class="btn-icon"
                        onclick="deleteTodo('${todo.todoNo}')"
                        title="삭제"
                      >
                        🗑️
                      </button> 
                    </div>
                  </div>
                </c:forEach>
                  </div> 
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 자바스크립트 함수 -->
    <script>
      /* 완료 여부 체크 함수 */
      function toggleTodo(todoNo) { //todoNo를 매개변수로 받는 함수
        console.log("todoNo : ", todoNo);
        
        //todo${todoNo}에 해당하는 ID를 찾음
        const checkbox = document.querySelector('#todo' + todoNo);
        console.log("checkbox ", checkbox)

        //todo${todoNo}를 포함하고 있는 todo-item div를 찾음
        const todoItem = checkbox.closest(".todo-item");
        console.log("todoItem ", todoItem)

        // 완료 상태에 따라 completed 클래스를 추가 또는 제거
        todoItem.classList.toggle("completed");
        
        fetch(`/todo/` + todoNo + `/completed/update`, { method : "POST" })
        .catch((error) => {
          todoItem.classList.toggle("completed");
          console.error(error);
        })
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
