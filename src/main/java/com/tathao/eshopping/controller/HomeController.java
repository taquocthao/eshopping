package com.tathao.eshopping.controller;

import com.tathao.eshopping.model.command.ProductSkuCommand;
import com.tathao.eshopping.model.dto.ProductSkuDTO;
import com.tathao.eshopping.service.ProductService;
import com.tathao.eshopping.service.ProductSkuService;
import com.tathao.eshopping.ultils.CoreConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
public class HomeController extends ApplicationObjectSupport {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductSkuService productSkuService;

	@RequestMapping(value = {"/admin.html", "/admin/home.html"})
	public ModelAndView homePageAdmin() {
		ModelAndView mav = new ModelAndView("homePageAdmin");
		return mav;
	}

	@RequestMapping(value = {"/home.html"})
	public ModelAndView homePageShopper(@ModelAttribute(name = "item") ProductSkuCommand command) {
		ModelAndView mav = new ModelAndView("homePageShopper");
		try {
			Map<String, Object> properties = buidProperties4Search(command);
			Object[] result = productSkuService.findByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), CoreConstants.MAX_PAGE_ITEMS);
			command.setTotalItems(Integer.valueOf(result[0].toString()));
			command.setListResult((List<ProductSkuDTO>) result[1]);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return mav;
	}

	private Map<String, Object> buidProperties4Search(ProductSkuCommand command) {
		Map<String, Object> props = new HashMap<String, Object>();

		return props;
	}
}
