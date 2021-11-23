package com.tathao.eshopping.controller;

import com.tathao.eshopping.model.command.UserCommand;
import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.service.CustomerService;
import com.tathao.eshopping.service.UserService;
import com.tathao.eshopping.ultils.CoreConstants;
import com.tathao.eshopping.ultils.SecurityUtil;
import com.tathao.eshopping.ultils.WebConstants;
import com.tathao.eshopping.validator.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends ApplicationObjectSupport {

    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/signup.html")
    public ModelAndView signup(@ModelAttribute(value = CoreConstants.FORM_MODEL_KEY) UserCommand command, BindingResult result) {
        ModelAndView mav = new ModelAndView("/signup");
        String crudaction = command.getCrudaction();
        logger.info(">>> UserController method: signup, crudaction: " + crudaction);
        try {
            if(!StringUtils.isEmpty(crudaction) && WebConstants.INSERT_OR_UPDATE.equals(crudaction)) {
                userValidator.validate(command, result);
                if(!result.hasErrors()) {
                    UserDTO user = command.getPojo();
                    user = userService.registerCustomer(user);
                    SecurityUtil.loginUser(user);
                    return new ModelAndView("redirect:/home.html");
                }
            }
        } catch (Exception e) {
            logger.error("UserController error: " + e.getMessage());
            mav.addObject(WebConstants.ALER, CoreConstants.TYPE_DANGER);
            mav.addObject(WebConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("message.signup.failure"));
        }
        return mav;
    }



}
