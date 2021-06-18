package com.tathao.eshopping.ultils;

import com.tathao.eshopping.model.command.AbstractCommand;
import org.displaytag.util.ParamEncoder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static void initSearchBean(HttpServletRequest request, AbstractCommand command) {
        if(StringUtils.isEmpty(command.getSortExpression())) {
            command.setSortExpression("createdDate");
        }
        if(StringUtils.isEmpty(command.getSortDirection())) {
            command.setSortDirection(CoreConstants.SORT_DESC);
        }
        String pageString = request.getParameter(new ParamEncoder("tableList").encodeParameterName("p"));
        if(!StringUtils.isEmpty(pageString)) {
            int page = Integer.parseInt(pageString);
            page = (page - 1) * CoreConstants.MAX_PAGE_ITEMS;
            command.setFirstItem(page);
        }

        String columnSort = request.getParameter(new ParamEncoder("tableList").encodeParameterName("s"));
        String sortDirection = request.getParameter(new ParamEncoder("tableList").encodeParameterName("o"));
        if(!StringUtils.isEmpty(sortDirection) && !StringUtils.isEmpty(columnSort)) {
            command.setSortExpression(columnSort);
            command.setSortDirection(sortDirection);
        }
        command.setMaxPageItems(CoreConstants.MAX_PAGE_ITEMS);
    }

}
