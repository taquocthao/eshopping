package com.tathao.eshopping.validator;

import com.tathao.eshopping.model.command.CatGroupCommand;
import com.tathao.eshopping.model.dto.CatGroupDTO;
import com.tathao.eshopping.service.CatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CatGroupValidator extends ApplicationObjectSupport implements Validator {

    @Autowired
    private CatGroupService catGroupService;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        CatGroupCommand command = (CatGroupCommand) o;
        trimField(command);
        checkRequiredFields(command, errors);
        checkExists(command, errors);
    }

    private void trimField(CatGroupCommand command) {
        CatGroupDTO pojo = command.getPojo();
        if(pojo == null) {
            return;
        }
        //code
        if(!StringUtils.isEmpty(pojo.getCode())) {
            pojo.setCode(pojo.getCode().trim());
        }
        // name
        if(!StringUtils.isEmpty(pojo.getName())) {
            pojo.setName(pojo.getName().trim());
        }
        // description
        if(!StringUtils.isEmpty(pojo.getDescription())) {
            pojo.setDescription(pojo.getDescription().trim());
        }
        command.setPojo(pojo);
    }

    private void checkRequiredFields(CatGroupCommand command, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.code", "errors.required",
                new Object[]{this.getMessageSourceAccessor().getMessage("label.catgroup.code")}, "non-empty value required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pojo.name", "errors.required",
                new Object[]{this.getMessageSourceAccessor().getMessage("label.catgroup.name")}, "non-empty value required");
    }


    private void checkExists(CatGroupCommand command, Errors errors) {
        CatGroupDTO pojo = command.getPojo();
        if(pojo != null && pojo.getCatGroupId() == null && pojo.getCode() != null) { // case add new
            CatGroupDTO dto = catGroupService.findByCode(pojo.getCode());
            if(dto != null && dto.getCode().equals(pojo.getCode())) {
                errors.rejectValue("pojo.code", "error.duplicated", new Object[]{this.getMessageSourceAccessor().getMessage("label.catgroup.code")}, "value invalid.");
            }
        }
    }

}
