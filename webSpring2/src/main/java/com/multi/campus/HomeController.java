package com.multi.campus;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller //annotation -> Controller, RestController
public class HomeController {
	

	@RequestMapping(value = "/", method = RequestMethod.GET)//get방식
	public String home(Model model) {
		//매개변수에 Model변수를 선언하고 Model에 필요한 데이터를 세팅하면 뷰페이지에서 사용할 수 있다.
		model.addAttribute("num", 1234);
		model.addAttribute("name", "방탄소년단");
		return "home";
	}
	
	//	localhost:9090/campus/test?name=홍
	@RequestMapping("/test")//get방식일때
	public ModelAndView test(HttpServletRequest request) {
		
		String name = request.getParameter("name");
		System.out.println("test(이름):"+name);
		
		//데이터와 뷰페이지정보 함꼐 가질 수 있는 클래스
		ModelAndView mav = new ModelAndView();
		
		//데이터 세팅
		mav.addObject("num", 5678);
		mav.addObject("name", name);
		
		//뷰페이지 세팅
		mav.setViewName("home");
		
		return mav;
	}
	//localhost:9090/campus/test?addr=서울시
	@RequestMapping("/test2")
	public ModelAndView test2(String addr) {
		System.out.println("test2(주소):"+addr);
		
		//	/test2를 처리한 후 /test3매핑주소로 이동하도록 하기
		// 매핑에서 다른 매핑으로 이동하는 방법
		ModelAndView mav = new ModelAndView();
		mav.addObject("num", 9999);
		mav.addObject("name", "손흥민");
		//						  접속할 다른 컨트롤러 매핑주소를 기술한다.
		mav.setViewName("redirect:test3");
		return mav;
	}
	
	//localhost:9090/campus/test3?num=120&name=세종대왕
	// dto의 변수가 정수형이면 문자열을 request하여 정수형으로 형변환해준다.
	@RequestMapping("/test3")
	public String test3(TestDTO dto, Model model) {
		System.out.println("num->"+(dto.getNum()+1000));
		System.out.println(dto.toString());
		
		model.addAttribute("num", 7777);
		model.addAttribute("name", "박지성");
		return "home";
	}
	
	//localhost:9090/campus/test4?tel=010-1234-5678
	@GetMapping("/test4")
	public String test4(String tel) {
		System.out.println("test4(연락처):"+tel);
		return "home";
	}
}
