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
    <title>TodoList - 카테고리 관리</title>
    <link rel="stylesheet" href="/css/htmlstyle.css" />
  </head>
  <body>
    <!-- 헤더 -->
    <header class="header">
      <div class="header-content">
        <div class="header-left">
          <a href="/todo" class="btn btn-outline">← 할일 목록으로</a>
          <h1>📝 카테고리 관리</h1>
        </div>
        <div class="header-right">
          <span class="user-info">
            <sec:authentication property="name" />님의 카테고리
          </span>
        </div>
      </div>
    </header>

    <div class="main-container">
      <div class="content-grid category-grid">
        <!-- 새 카테고리 추가 -->
        <div class="sidebar">
          <div class="card">
            <div class="card-header">
              <h3>➕ 새 카테고리 추가</h3>
            </div>
            <div class="card-body">
              <form
                id="categoryForm"
                action="<c:url value='category' />"
                method="post"
              >
                <div class="form-group">
                  <label for="categoryName">카테고리 이름</label>
                  <input
                    type="text"
                    id="categoryName"
                    name="categoryName"
                    placeholder="카테고리 이름을 입력하세요"
                    required
                  />
                </div>
                <button type="submit" class="btn btn-primary">
                  카테고리 추가
                </button>
              </form>
            </div>
          </div>
        </div>

        <!-- 카테고리 목록 -->
        <div class="main-content">
          <div class="card">
            <div class="card-header">
              <h3>📂 카테고리 목록</h3>
            </div>
            <div class="card-body">
              <!-- 카테고리 목록 -->
              <div class="category-list">
                <c:forEach
                  var="category"
                  items="${categoryList}"
                  varStatus="status"
                >
                  <div
                    class="category-item"
                    id="category-${category.categoryNo}"
                  >
                    <div class="category-info">
                      <span class="category-number">#${status.index + 1}</span>
                      <span class="category-name"
                        >${category.categoryName}</span
                      >
                    </div>
                    <div class="category-actions">
                      <a
                        href="/category/${category.categoryNo}/detail"
                        class="btn-icon"
                        title="수정"
                        >✏️</a
                      >
                      <form
                        action="/category/${category.categoryNo}/delete"
                        method="POST"
                        style="display: inline"
                        onsubmit="return confirm('정말 삭제하시겠습니까?');"
                      >
                        <button type="submit" class="btn-icon" title="삭제">
                          🗑️
                        </button>
                      </form>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </div>
          </div>
        </div>
      </div>

    <script>
      /* editingCategoryId 선언 */
      let editingCategoryId = null;

      /* 카테고리 수정 버튼 클릭 시 인라인 편집 UI로 변환 */
      //categoryNo, categoryName 매개변수로 받기
      function editCategory(categoryNo, currentName) {
        //해당 카테고리 번호를 가진 HTML 요소(id="category-번호")를 찾음
        const categoryItem = document.getElementById(`category-${categoryNo}`);
        //해당 요소 안에서 카테고리 이름이 표시된 부분(.category-name 클래스를 가진 요소)을 찾음
        const categoryNameSpan = categoryItem.querySelector(".category-name");

        //editingCategoryId와 categoryNo가 같으면 cancelEdit(수정 취소) 실행
        //같은 항목을 '수정' 버튼으로 또 눌렀을 때 → 수정 모드를 취소하고 함수 종료
        if (editingCategoryId === categoryNo) {
          cancelEdit();
          return;
        }

        //editingCategoryId가 true면 cancelEdit(수정 취소) 실행
        //만약 다른 항목을 수정 중이었다면 (editingCategoryId가 null이 아니면) 기존에 열려있던 편집 모드를 먼저 닫아줌
        if (editingCategoryId) {
          cancelEdit();
        }

        //editingCategoryId에 매개변수로 받은 categoryNo 대입
        editingCategoryId = categoryNo;

        // 카테고리 이름 부분을 인풋창으로 변경하고, 엔터 또는 ESC 키 이벤트 처리 연결
        categoryNameSpan.innerHTML = `
          <input type="text" class="edit-input" value="${currentName}"
                 onkeydown="handleEditKeydown(event, ${categoryNo})">
        `;

        // 수정/삭제 버튼 영역을 '저장'과 '취소' 버튼으로 교체
        const actionsDiv = categoryItem.querySelector(".category-actions");
        actionsDiv.innerHTML = `
          <form action="/category/${categoryNo}/update" method="post" style="display:inline;">
            <input type="text" name="categoryName" value="${currentName}" required style="width:120px"/>
            <button class="btn-icon" type="submit" title="저장">💾</button>
          </form>
          <form action="/category/${categoryNo}/delete" method="post" style="display:inline;" onsubmit="return confirm('정말 삭제할까요?');">
            <button class="btn-icon" type="submit" title="삭제">❌</button>
          </form>
        `;

        // 방금 생성한 입력 필드(.edit-input)를 찾음
        const input = categoryNameSpan.querySelector(".edit-input");
        // 입력 필드에 자동으로 커서를 이동시킴
        input.focus();
        // 입력된 기존 텍스트를 전체 선택하여 바로 수정할 수 있도록 함
        input.select();
      }

      // 엔터/ESC 처리
      function handleEditKeydown(event, categoryNo) {
        if (event.key === "Enter") {
          saveCategory(categoryNo);
        } else if (event.key === "Escape") {
          cancelEdit();
        }
      }

      // 카테고리 저장
      function saveCategory(categoryNo) {
        //해당 카테고리 번호를 가진 HTML 요소(id="category-번호")를 찾음
        const categoryItem = document.getElementById(`category-${categoryNo}`);
        //해당 요소 안에서 카테고리 이름이 표시된 부분(.edit-input 클래스를 가진 요소)을 찾음
        const input = categoryItem.querySelector(".edit-input");
        //사용자가 입력한 새 카테고리 이름에서 앞뒤 공백을 제거하고 변수에 저장
        const newName = input.value.trim();

        //newName이 공백이면
        if (!newName) {
          alert("카테고리 이름을 입력해주세요.");
          return;
        }

        // 해당 카테고리 항목 안에서 '카테고리 이름'이 표시된 부분(.category-name 클래스)을 찾음
        const categoryNameSpan = categoryItem.querySelector(".category-name");

        //categoryNameSpan을 새로 입력한 categoryNameSpan으로 변경경
        categoryNameSpan.textContent = newName;

        // 해당 카테고리 항목 안에서 '버튼'(.category-actions 클래스)을 찾음
        const actionsDiv = categoryItem.querySelector(".category-actions");
        //버튼을 다시 수정과 삭제로 변경
        actionsDiv.innerHTML = `
          <button class="btn-icon" onclick="editCategory(${categoryNo}, '${newName}')" title="수정">✏️</button>
          <button class="btn-icon" onclick="deleteCategory(${categoryNo})" title="삭제">🗑️</button>
        `;

        // 현재 수정 중인 editingCategoryId 초기화 (수정 종료 상태로 변경)
        editingCategoryId = null;
      }

      // 편집 취소
      function cancelEdit() {
        if (!editingCategoryId) return;
        location.reload(); // 실무에서는 이전 값으로 복구하는 방식이 적절
      }
    </script>
  </body>
</html>
