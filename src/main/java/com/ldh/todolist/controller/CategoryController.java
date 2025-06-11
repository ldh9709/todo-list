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

import com.ldh.todolist.dto.CategoryDto;
import com.ldh.todolist.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	/* category/new에 접근했을 때,category/create.jsp 파일을 찾아 사용자에게 렌더링 */
	@GetMapping("/new")
	public String showCreateForm() {
		return "category/create"; ///WEB-INF/views/category/create.jsp
	}
	
	//Post로 form제출 시 추가 실행
	@PostMapping
	//@ModelAttribute : JSP -> Controller로 오는 데이터 자동 바인딩
	public String createCategory(@ModelAttribute CategoryDto categoryDto) {
		
		categoryService.saveCategory(categoryDto);
		
		return "redirect:/todo/user/" + categoryDto.getUsersNo() + "/list";
	}
	
	//할 일 조회
	@GetMapping("/{categoryNo}")
	//Model : Controller -> JSP로 데이터를 넘겨준다.
	public String getCategoryDetail(@PathVariable Long categoryNo, Model model) {
		
		CategoryDto categoryDto = categoryService.findById(categoryNo);
		
		//JSP에서 ${이름}으로 객체 꺼낼 수 있게 설정
		model.addAttribute("category", categoryDto);
		return "category/detail";
	}
	
	//할 일 수정 폼
	@GetMapping("/{categoryNo}/update")
	public String showUpdateForm(@PathVariable Long categoryNo, Model model) {
		//categoryNo로 categoryDto 정보 가져오기
        CategoryDto categoryDto = categoryService.findById(categoryNo);
        //model에 todo로 세팅
        model.addAttribute("category", categoryDto);
        //URL 반환
        return "category/update";
    }
	
	//할 일 수정
	@PostMapping("/{categoryNo}/update")
	public String updateCategory(@ModelAttribute CategoryDto categoryDto) {
		//할 일 수정 실행
		categoryService.updateCategory(categoryDto);
		//할 일 페이지로 리다이렉트
		return "redirect:/todo/user/" + categoryDto.getUsersNo() + "/list";
	}
	
	//할 일 삭제
	@PostMapping("/{categoryNo}/delete")
	public String deleteCategory(@PathVariable Long categoryNo, @RequestParam Long usersNo) {
		//삭제 실행
		categoryService.deleteCategory(categoryNo);
		//리스트로 리다이렉트
		return "redirect:/todo/user/" + usersNo + "/list";
	}
	
	@GetMapping("/user/{usersNo}/list")
	public String getTodoListByUsersNo(@PathVariable Long usersNo, Model model) {
		
		List<CategoryDto> categoryList = categoryService.findByUsersNo(usersNo);
		
		model.addAttribute("categoryList", categoryList);
		
		return "category/list";
	}
	
	
	
}
