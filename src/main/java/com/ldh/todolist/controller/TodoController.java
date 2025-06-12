package com.ldh.todolist.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ldh.todolist.auth.PrincipalDetails;
import com.ldh.todolist.dto.TodoDto;
import com.ldh.todolist.service.CategoryService;
import com.ldh.todolist.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

	private final TodoService todoService;

	private final CategoryService categoryService;

	/* todo/new에 접근했을 때,todo/create.jsp 파일을 찾아 사용자에게 렌더링 */
	@GetMapping("/new")
	public String showCreateForm() {
		return "todo/create"; /// WEB-INF/views/todo/create.jsp
	}

	// Post로 form제출 시 추가 실행
	@PostMapping
	// @ModelAttribute : JSP -> Controller로 오는 데이터 자동 바인딩
	public String createTodo(@ModelAttribute TodoDto todoDto, @AuthenticationPrincipal PrincipalDetails principal) {
		
		Long usersNo = principal.getUsersNo();
		
		todoDto.setUsersNo(usersNo);
		
		todoService.saveTodo(todoDto);

		return "redirect:/todo";
	}

	// 할 일 조회
	@GetMapping("/{todoNo}")
	// Model : Controller -> JSP로 데이터를 넘겨준다.
	public String getTodoDetail(@PathVariable Long todoNo, Model model) {

		TodoDto todoDto = todoService.findById(todoNo);

		// JSP에서 ${이름}으로 객체 꺼낼 수 있게 설정
		model.addAttribute("todo", todoDto);
		return "todo/detail";
	}

	// 할 일 수정 폼
	@GetMapping("/{todoNo}/update")
	public String showEditForm(@PathVariable Long todoNo, Model model) {
		// todoNo로 todoDto 정보 가져오기
		TodoDto todoDto = todoService.findById(todoNo);
		// model에 todo로 세팅
		model.addAttribute("todo", todoDto);
		// URL 반환
		return "todo/update";
	}

	// 할 일 수정
	@PostMapping("/{todoNo}/update")
	public String updateTodo(@ModelAttribute TodoDto todoDto) {
		// 할 일 수정 실행
		todoService.updateTodo(todoDto);
		// 할 일 페이지로 리다이렉트
		return "redirect:/todo/user/" + todoDto.getUsersNo() + "/list";
	}
	
	// 할 일 완료 여부
	@PostMapping("/{todoNo}/completed/update")
	public String updateTodoCompleted(@PathVariable("todoNo") Long todoNo) {
		
		//todoNo로 todo 찾기
		TodoDto findTodo = todoService.findById(todoNo);
		
	    //완료 상태 반전(true → false, false → true)
	    findTodo.setTodoCompleted(!findTodo.isTodoCompleted());
	    
		//할 일 수정 실행
		todoService.updateTodoCompleted(findTodo);
		
		//할 일 페이지로 리다이렉트
		return "redirect:/todo";
	}

	// 할 일 삭제
	@PostMapping("/{todoNo}/delete")
	public String deleteTodo(@PathVariable Long todoNo, @RequestParam Long usersNo) {
		// 삭제 실행
		todoService.deleteTodo(todoNo);
		// 리스트로 리다이렉트
		return "redirect:/todo/user/" + usersNo + "/list";
	}

	@GetMapping("/user/{usersNo}/list")
	public String getTodoListByUsersNo(@PathVariable Long usersNo, Model model) {

		List<TodoDto> todoList = todoService.findByUsersNo(usersNo);

		model.addAttribute("todoList", todoList);

		return "todo/list";
	}

	@GetMapping("")
	public String main(Model model, @AuthenticationPrincipal PrincipalDetails principal) {

		Long usersNo = principal.getUsersNo();
		model.addAttribute("todoList", todoService.findByUsersNo(usersNo));
		model.addAttribute("categoryList", categoryService.findByUsersNo(usersNo));
		System.out.println("todoService.findByUsersNo(usersNo) : " + todoService.findByUsersNo(usersNo));
		System.out.println("categoryService.findByUsersNo(usersNo) : " + categoryService.findByUsersNo(usersNo));
		System.out.println("main");
		return "todo"; // /WEB-INF/views/main.jsp
	}	

}
