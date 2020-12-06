package com.tathao.eshopping.controller;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class MainController extends ApplicationObjectSupport {

	@RequestMapping(value = {"/admin", "/admin/home.html"})
	public ModelAndView homePageShopper() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("homePage");
		return mav;
	}
	
}
