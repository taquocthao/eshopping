package com.tathao.eshopping.validator;

import com.tathao.eshopping.model.command.UserCommand;
import com.tathao.eshopping.model.dto.UserDTO;
import com.tathao.eshopping.service.UserService;
import com.tathao.eshopping.ultils.WebConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator extends ApplicationObjectSupport implements Validator  {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserCommand.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserCommand command = (UserCommand) o;
        trimInput(command);
        checkRequiredInput(command, errors);
        checkValidPhoneNumber(command.getPojo(), errors);
        checkValidUsername(command.getPojo(), errors);
        checkExistAccount(command, errors);
    }

    private void trimInput(UserCommand command) {
        UserDTO userDTO = command.getPojo();
        userDTO.setFullName(userDTO.getFullName().trim());
        userDTO.setUsername(userDTO.getUsername().trim());
//        userDTO.setPassword(userDTO.getPassword().trim());
        command.setRe_password(command.getRe_password().trim());
        userDTO.setPhoneNumber(userDTO.getPhoneNumber().trim());
        command.setPojo(userDTO);
    }

    private void checkRequiredInput(UserCommand command, Errors errors) {
        UserDTO userDTO = command.getPojo();
        String password = userDTO.getPassword();
        String rePassword = command.getRe_password();
        if(StringUtils.isBlank(userDTO.getFullName())) {
            errors.rejectValue("pojo.fullName", "errors.required",
                    new Object[]{this.getMessageSourceAccessor().getMessage("label.fullname.placeholder")}, "non-empty value required");
        }
        if(StringUtils.isBlank(userDTO.getPhoneNumber())) {
            errors.rejectValue("pojo.phoneNumber", "errors.required",
                    new Object[]{this.getMessageSourceAccessor().getMessage("label.phone")}, "non-empty value required");
        }
        if(StringUtils.isBlank(userDTO.getUsername())) {
            errors.rejectValue("pojo.username", "errors.required",
                    new Object[]{this.getMessageSourceAccessor().getMessage("label.username")}, "non-empty value required");
        }
        if(StringUtils.isBlank(password)) {
            errors.rejectValue("pojo.password", "errors.required",
                    new Object[]{this.getMessageSourceAccessor().getMessage("label.password")}, "non-empty value required");
        }
        if(command.getCrudaction().equals(WebConstants.INSERT_OR_UPDATE)) {
            if(StringUtils.isBlank(rePassword)) {
                errors.rejectValue("re_password", "errors.required",
                        new Object[]{this.getMessageSourceAccessor().getMessage("label.re-password.placeholder")}, "non-empty value required");
            }
            // check match password
            if(!StringUtils.isBlank(password) && !StringUtils.isBlank(rePassword) && !rePassword.equals(password)) {
                errors.rejectValue("re_password", "errors.notmatch",
                        new Object[]{this.getMessageSourceAccessor().getMessage("label.password")}, "not match");
            }
        }
    }


    private void checkExistAccount(UserCommand command, Errors errors) {
        if(!StringUtils.isBlank(command.getCrudaction()) && WebConstants.INSERT_OR_UPDATE.equals(command.getCrudaction())) {
            UserDTO userDTO = userService.findByUserName(command.getPojo().getUsername());
            if(userDTO != null) {
                errors.rejectValue("pojo.username", "errors.exists",
                        new Object[] {this.getMessageSourceAccessor().getMessage("label.username")}, "existed");
            }
        }
    }

    private void checkValidPhoneNumber(UserDTO pojo, Errors errors) {
        if(!StringUtils.isBlank(pojo.getPhoneNumber())) {
            Pattern pattern = Pattern.compile("\\d{10}");
            Matcher matcher = pattern.matcher(pojo.getPhoneNumber());
            if(!matcher.matches()) {
               errors.rejectValue("pojo.phoneNumber", "errors.incorrect.format", new Object[]{this.getMessageSourceAccessor().getMessage("label.phone")}, "incorrect format");
            }
        }
    }

    private void checkValidUsername(UserDTO pojo, Errors errors) {

    }
}
