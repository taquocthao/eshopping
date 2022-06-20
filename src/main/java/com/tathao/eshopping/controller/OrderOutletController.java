package com.tathao.eshopping.controller;

import com.tathao.eshopping.model.command.OrderOutletCommand;
import com.tathao.eshopping.model.dto.OrderOutletDTO;
import com.tathao.eshopping.service.OrderOutletService;
import com.tathao.eshopping.ultils.CoreConstants;
import com.tathao.eshopping.ultils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderOutletController extends ApplicationObjectSupport {

    @Autowired
    private OrderOutletService orderOutletService;

    @RequestMapping(value = "/admin/order/list.html")
    public ModelAndView list(@ModelAttribute(CoreConstants.FORM_MODEL_KEYS) OrderOutletCommand command, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/admin/order/list");
        logger.info("get list order");
        try {
            executeSearchOrder(mav, command, request);
        } catch (Exception e) {
            logger.error("POSITION OrderOutletController - method list:", e);
        }
        return mav;
    }

    @RequestMapping(value = "/admin/order/pos.html")
    public ModelAndView pos(@ModelAttribute(CoreConstants.FORM_MODEL_KEY) OrderOutletCommand command) {
        ModelAndView mav = new ModelAndView("/admin/order/pos");
        try {

        } catch (Exception e) {
            logger.error("POSITION OrderOutletController - method pos() error", e);
        }
        return mav;
    }

    private void executeSearchOrder(ModelAndView mav, OrderOutletCommand command, HttpServletRequest request) {
        Integer totalOrder = 0;
        Integer totalWaitingForConfirm = 0;
        Integer totalPicking = 0;
        Integer totalDelivering = 0;
        Integer totalSuccess = 0;
        Integer totalCancel = 0;
        Integer totalReturn = 0;
        Integer totalItems = 0;
        List<OrderOutletDTO> listOrders = new ArrayList<>();

        Map<String, Object> buildProperties = buildProperties4Search(request, command);
        StringBuilder whereClause = new StringBuilder("1 = 1");
        if(command.getPojo() != null && command.getPojo().getStatus() != null) {
            whereClause.append("AND A.status = ").append(command.getPojo().getStatus());
        }
        logger.info("executeSearchOrder - properties " + buildProperties.keySet() + " - where clause : " + whereClause.toString());
        Object[] result = orderOutletService.findByProperties(buildProperties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getTotalItems(), whereClause.toString());

        totalItems = Integer.parseInt(result[0].toString());
        listOrders = (List<OrderOutletDTO>) result[1];
        totalWaitingForConfirm = Integer.parseInt(result[2].toString());
        totalPicking = Integer.parseInt(result[3].toString());
        totalDelivering = Integer.parseInt(result[4].toString());
        totalSuccess = Integer.parseInt(result[5].toString());
        totalCancel = Integer.parseInt(result[6].toString());
        totalReturn = Integer.parseInt(result[7].toString());
        totalOrder = totalWaitingForConfirm + totalPicking + totalDelivering + totalSuccess + totalCancel + totalReturn;

        command.setListResult(listOrders);
        command.setTotalItems(totalItems);
        command.setTotal(totalOrder);
        command.setTotalWaitingForConfirm(totalWaitingForConfirm);
        command.setTotalPicking(totalPicking);
        command.setTotalDelivering(totalDelivering);
        command.setTotalSuccess(totalSuccess);
        command.setTotalCancel(totalCancel);
        command.setTotalReturn(totalReturn);
    }

    private Map<String, Object> buildProperties4Search(HttpServletRequest request, OrderOutletCommand command) {
        RequestUtils.initSearchBean(request, command);
        Map<String, Object> properties = new HashMap<>();
        OrderOutletDTO dto = command.getPojo();
        if(dto != null) {
            if (!StringUtils.isEmpty(dto.getCode())) {
                properties.put("code", dto.getCode().trim());
            }
            if (dto.getCustomer() != null && !StringUtils.isEmpty(dto.getCustomer().getUser().getFullName())) {
                properties.put("customer.user.fullName", dto.getCustomer().getUser().getFullName().trim());
            }
        }
        return properties;
    }

}
