package com.tathao.eshopping.controller.admin;

import com.tathao.eshopping.model.command.CatGroupCommand;
import com.tathao.eshopping.model.dto.CatGroupDTO;
import com.tathao.eshopping.service.CatGroupService;
import com.tathao.eshopping.ultils.CoreConstants;
import com.tathao.eshopping.ultils.RequestUtils;
import com.tathao.eshopping.validator.CatGroupValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CatGroupController extends ApplicationObjectSupport {

    private static final Logger logger = Logger.getLogger(CatGroupController.class);

    @Autowired
    private CatGroupService catGroupService;

    @Autowired
    private CatGroupValidator catGroupValidator;

    @RequestMapping(value = "/admin/catgroup/list.html")
    public ModelAndView list(@ModelAttribute(CoreConstants.FORM_MODEL_KEYS) CatGroupCommand command, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/admin/catgroup/list");
        try {
            Map<String, Object> properties = buildProperties4AdminSearch(request, command);
            StringBuilder whereClause = new StringBuilder("1 = 1");
            if(command.getPojo() != null && command.getPojo().getStatus() != null) {
                whereClause.append(" AND A.status = ").append(command.getPojo().getStatus());
            }
            Object[] result = catGroupService.findByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems(), whereClause.toString());
            command.setListResult((List<CatGroupDTO>) result[1]);
            command.setTotalItems(Integer.parseInt(result[0].toString()));
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return mav;
    }


    @RequestMapping(value = "/admin/catgroup/edit.html")
    public ModelAndView edit(@ModelAttribute(CoreConstants.FORM_MODEL_KEY) CatGroupCommand command,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/admin/catgroup/edit");
        CatGroupDTO pojo = command.getPojo();
        String crudaction = command.getCrudaction();
        try {
            if(!StringUtils.isEmpty(crudaction)) {
                catGroupValidator.validate(command, bindingResult);
                if(!bindingResult.hasErrors()) {
                    catGroupService.saveOrUpdate(pojo);
                    if (pojo.getCatGroupId() != null) { // update
                        redirectAttributes.addFlashAttribute(CoreConstants.MESSAGE_RESPONSE,
                                this.getMessageSourceAccessor().getMessage("label.catgroup.edit.successful"));
                    } else { // insert
                        redirectAttributes.addFlashAttribute(CoreConstants.MESSAGE_RESPONSE,
                                this.getMessageSourceAccessor().getMessage("label.catgroup.add.successful"));
                    }
                    redirectAttributes.addFlashAttribute(CoreConstants.ALTER, CoreConstants.TYPE_SUCCESS);
                    return new ModelAndView("redirect:/admin/catgroup/list.html");
                }
            }
            if(pojo != null && !StringUtils.isEmpty(pojo.getCode())) {
                pojo = catGroupService.findByCode(pojo.getCode());
                command.setPojo(pojo);
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            mav.addObject(CoreConstants.ALTER, CoreConstants.TYPE_DANGER);
            if (pojo != null && pojo.getCatGroupId() != null) {
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("label.catgroup.update.failure"));
            } else {
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("label.catgroup.add.failure"));
            }
        }
        referenceData4AdminEdit(mav);
        return mav;
    }

    private Map<String, Object> buildProperties4AdminSearch(HttpServletRequest request, CatGroupCommand command) {
        RequestUtils.initSearchBean(request, command);
        Map<String, Object> properties = new HashMap<>();
        CatGroupDTO dto = command.getPojo();
        if(dto != null) {
            if (!StringUtils.isEmpty(dto.getCode())) {
                properties.put("code", dto.getCode().trim());
            }
            if (!StringUtils.isEmpty(dto.getName())) {
                properties.put("name", dto.getName().trim());
            }
        }
        return properties;
    }

    private void referenceData4AdminEdit(ModelAndView mav) {
        List<CatGroupDTO> catGroupParents = catGroupService.findParentCatGroups();
        mav.addObject("catGroupParents", catGroupParents);
    }

}
