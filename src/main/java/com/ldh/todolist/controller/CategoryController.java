package com.ldh.todolist.controller;

import java.util.List;

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
import com.ldh.todolist.dto.CategoryDto;
import com.ldh.todolist.dto.TodoDto;
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
	public String createCategory(@AuthenticationPrincipal PrincipalDetails principal, @ModelAttribute CategoryDto categoryDto) {
		Long usersNo = principal.getUsersNo();
		
		categoryDto.setUsersNo(usersNo);
		
		categoryService.saveCategory(categoryDto);
		
		return "redirect:/category";
	}
	
	//카테고리 조회
	@GetMapping("/{categoryNo}/detail")
	//Model : Controller -> JSP로 데이터를 넘겨준다.
	public String getCategoryDetail(@PathVariable("categoryNo") Long categoryNo, Model model) {

		CategoryDto category = categoryService.findById(categoryNo);
		
		// JSP에서 ${이름}으로 객체 꺼낼 수 있게 설정
		model.addAttribute("category", category);
		
		return "categoryDetail";
	}
	
	//카테고리 수정 폼
	@GetMapping("/{categoryNo}/update")
	public String showUpdateForm(@PathVariable("categoryNo") Long categoryNo, Model model) {
		//categoryNo로 categoryDto 정보 가져오기
        CategoryDto categoryDto = categoryService.findById(categoryNo);
        //model에 todo로 세팅
        model.addAttribute("category", categoryDto);
        //URL 반환
        return "category/update";
    }
	
	//카테고리 수정
	@PostMapping("/{categoryNo}/update")
	public String updateCategory(@ModelAttribute CategoryDto categoryDto) {
		//카테고리 수정 실행
		categoryService.updateCategory(categoryDto);
		//카테고리 페이지로 리다이렉트
		return "redirect:/category";
	}
	
	//카테고리 삭제
	@PostMapping("/{categoryNo}/delete")
	public String deleteCategory(@PathVariable("categoryNo") Long categoryNo) {
		//삭제 실행
		categoryService.deleteCategory(categoryNo);
		//리스트로 리다이렉트
		return "redirect:/category";
	}
	
	@GetMapping("/user/{usersNo}/list")
	public String getTodoListByUsersNo(@PathVariable("usersNo") Long usersNo, Model model) {
		
		List<CategoryDto> categoryList = categoryService.findByUsersNo(usersNo);
		
		model.addAttribute("categoryList", categoryList);
		
		return "category/list";
	}
	
	@GetMapping("")
	public String categoryMain(Model model, @AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("principal : " + principal);
		Long usersNo = principal.getUsersNo();
		model.addAttribute("categoryList", categoryService.findByUsersNo(usersNo));
		
		return "category"; // /WEB-INF/views/category.jsp
	}	
	
	
}
