package com.tathao.eshopping.controller;

import com.tathao.eshopping.model.entity.UserGroupEntity;
import com.tathao.eshopping.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class LoginController extends ApplicationObjectSupport {

	@Autowired
	private UserGroupService userGroupService;

	@RequestMapping(value = "/login.html")
	public ModelAndView shopperLogin() {
		ModelAndView mav = new ModelAndView("/login");
		return mav;
	}

	// User login via Social,
	// but not allow access basic info.
	// webapp will redirect to /signin.
	@RequestMapping(value = "/signin")
	public String signInPage() {
		return "redirect:/login";
	}

	private void referenceData(ModelAndView mav) {
		List<UserGroupEntity> userGroups = userGroupService.findAll();
		mav.addObject("userGroups", userGroups);
	}

}
