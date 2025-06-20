package com.ldh.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ldh.todolist.dto.UsersDto;
import com.ldh.todolist.service.UsersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
	
	private final UsersService usersService;
	
	//users/register에 접근했을 때,users/register.jsp 파일을 찾아 사용자에게 렌더링
	@GetMapping("/register")
	public String showRegisterForm() {
		return "users/register"; ///WEB-INF/views/users/register.jsp
	}
	
	//Post로 form제출 시 회원가입 실행
	@PostMapping("/register")
	//@ModelAttribute : JSP -> Controller로 오는 데이터 자동 바인딩
	public String register(@ModelAttribute UsersDto usersDto) {
		usersService.saveUser(usersDto);
	    return "redirect:/main.jsp";
	}
	
	//ID로 회원 조회
	@GetMapping("/id/{usersId}")
	//Model : Controller -> JSP로 데이터를 넘겨준다.
	public String detailUsersByUsersId(@PathVariable String usersId, Model model) {
		UsersDto usersDto = usersService.findById(usersId);
		//JSP에서 ${이름}으로 객체 꺼낼 수 있게 설정
		model.addAttribute("user", usersDto);
		return "users/detail";
	}
	
	//NO로 회원 조회
	@GetMapping("/no/{usersNo}")
	//Model : Controller -> JSP로 데이터를 넘겨준다.
	public String detailUsersByUsersNo(@PathVariable Long usersNo, Model model) {
		UsersDto usersDto = usersService.findByUsersNo(usersNo);
		//JSP에서 ${이름}으로 객체 꺼낼 수 있게 설정
		model.addAttribute("user", usersDto);
		return "users/detail";
	}
	
	//회원 수정
	@PostMapping("/{usersNo}/update")
	public String updateUser(@PathVariable Long usersNo, @ModelAttribute UsersDto usersDto) {
		//회원 수정 실행
		usersService.updateUser(usersDto);
		//회원 조회 페이지로 리다이렉트
		return "redirect:/users/" + usersDto.getUsersNo();
	}
	
	@PostMapping("/{usersNo}/delete")
	public String deleteUser(@PathVariable Long usersNo) {
		usersService.deleteUser(usersNo);
		return "redirect:/main";
	}
	
}
