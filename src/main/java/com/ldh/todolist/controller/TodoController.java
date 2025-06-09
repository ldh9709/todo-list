package com.ldh.todolist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ldh.todolist.dto.TodoDto;
import com.ldh.todolist.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {
	
	private final TodoService todoService;
	
	/* todo/new에 접근했을 때,todo/create.jsp 파일을 찾아 사용자에게 렌더링 */
	@GetMapping("/new")
	public String showCreateForm() {
		return "todo/create"; ///WEB-INF/views/todo/create.jsp
	}
	
	//Post로 form제출 시 추가 실행
	@PostMapping
	//@ModelAttribute : JSP -> Controller로 오는 데이터 자동 바인딩
	public String createTodo(@ModelAttribute TodoDto todoDto) {
		
		todoService.saveTodo(todoDto);
		
		return "redirect:/todo/user/" + todoDto.getUsersNo() + "/list";
	}
	
	//할 일 조회
	@GetMapping("/{todoNo}")
	//Model : Controller -> JSP로 데이터를 넘겨준다.
	public String getTodoDetail(@PathVariable int todoNo, Model model) {
		
		TodoDto todoDto = todoService.findById(todoNo);
		
		//JSP에서 ${이름}으로 객체 꺼낼 수 있게 설정
		model.addAttribute("todo", todoDto);
		return "todo/detail";
	}
	
	//할 일 수정 폼
	@GetMapping("/{todoNo}/update")
	public String showEditForm(@PathVariable int todoNo, Model model) {
		//todoNo로 todoDto 정보 가져오기
        TodoDto todoDto = todoService.findById(todoNo);
        //model에 todo로 세팅
        model.addAttribute("todo", todoDto);
        //URL 반환
        return "todo/update";
    }
	
	//할 일 수정
	@PostMapping("/{todoNo}/update")
	public String updateTodo(@ModelAttribute TodoDto todoDto) {
		//할 일 수정 실행
		todoService.updateTodo(todoDto);
		//할 일 페이지로 리다이렉트
		return "redirect:/todo/user/" + todoDto.getUsersNo() + "/list";
	}
	
	//할 일 삭제
	@PostMapping("/{todoNo}/delete")
	public String deleteTodo(@PathVariable int todoNo, @RequestParam int usersNo) {
		//삭제 실행
		todoService.deleteTodo(todoNo);
		//리스트로 리다이렉트
		return "redirect:/todo/user/" + usersNo + "/list";
	}
	
	@GetMapping("/user/{usersNo}/list")
	public String getTodoListByUsersNo(@PathVariable int usersNo, Model model) {
		
		List<TodoDto> todoList = todoService.findByUsersNo(usersNo);
		
		model.addAttribute("todoList", todoList);
		
		return "todo/list";
	}
	
	
	
}
