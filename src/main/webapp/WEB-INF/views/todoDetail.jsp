<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>할일 수정</title>
  <link rel="stylesheet" href="/css/htmlstyle.css" />
</head>

<body>
  <div class="main-container">
    <div class="card">
      <div class="card-header">
        <h3>✏️ 할일 수정</h3>
      </div>

      <div class="card-body">
        <form action="/todo/${todo.todoNo}/update" method="post">
          <!-- 제목 -->
          <div class="form-group">
            <label for="todoTitle">제목</label>
            <input type="text" id="todoTitle" name="todoTitle"
                   value="${todo.todoTitle}" required />
          </div>

          <!-- 내용 -->
          <div class="form-group">
            <label for="todoContent">내용</label>
            <textarea id="todoContent" name="todoContent" rows="4"
                      required>${todo.todoContent}</textarea>
          </div>

          <!-- 카테고리 -->
          <div class="form-group">
            <label for="categoryNo">카테고리</label>
            <select id="categoryNo" name="categoryNo" required>
              <c:forEach var="category" items="${categoryList}">
                <option value="${category.categoryNo}"
                        <c:if test="${category.categoryNo == todo.categoryNo}">selected</c:if>>
                  ${category.categoryName}
                </option>
              </c:forEach>
            </select>
          </div>

          <!-- 버튼 영역 -->
          <div class="form-group" style="display: flex; justify-content: flex-end; gap: 10px;">
            <a href="/todo" class="btn btn-outline">취소</a>
            <button type="submit" class="btn btn-primary">저장</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>
