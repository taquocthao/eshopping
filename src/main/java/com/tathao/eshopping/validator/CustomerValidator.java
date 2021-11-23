package com.tathao.eshopping.validator;

import com.tathao.eshopping.model.command.CustomerCommand;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CustomerValidator extends ApplicationObjectSupport implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CustomerCommand command = (CustomerCommand) o;
        trimInput(command);
        checkRequiredInput(command, errors);
        checkExistAccount(command, errors);
    }




    private void trimInput(CustomerCommand command) {
    }

    private void checkRequiredInput(CustomerCommand command, Errors errors) {
    }

    private void checkExistAccount(CustomerCommand command, Errors errors) {
    }
}
