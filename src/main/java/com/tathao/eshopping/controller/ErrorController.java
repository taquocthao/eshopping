package com.tathao.eshopping.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @RequestMapping(value = "errors")
    public ModelAndView renderErrorPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/common/errorPage");
        String errorMessage = "";
        int httpErrorCode = getErrorCode(request);
        switch (httpErrorCode) {
            case 400: {
                errorMessage = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMessage = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMessage = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMessage = "Http Error Code: 500. Internal Server Error";
                break;
            }
            default: {
                errorMessage = "Http Error Code: " + httpErrorCode;
                break;
            }
        }
        mav.addObject("errorMessage", errorMessage);
        return mav;
    }

    private int getErrorCode(HttpServletRequest request) {
        return (int) request.getAttribute("javax.servlet.error.status_code");
    }

}