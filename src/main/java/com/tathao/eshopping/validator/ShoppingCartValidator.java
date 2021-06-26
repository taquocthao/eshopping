package com.tathao.eshopping.validator;

import com.tathao.eshopping.model.command.CatGroupCommand;
import com.tathao.eshopping.model.dto.ShoppingCartRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ShoppingCartValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CatGroupCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ShoppingCartRequestDTO requestDTO = (ShoppingCartRequestDTO) o;
        checkRequiredInput(requestDTO, errors);
    }

    private void checkRequiredInput(ShoppingCartRequestDTO requestDTO, Errors errors) {
//        if(requestDTO.getCustomerCode() == null) {
//            errors.rejectValue("", );
//        }
    }
}
