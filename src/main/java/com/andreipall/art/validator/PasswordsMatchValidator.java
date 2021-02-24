package com.andreipall.art.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.andreipall.art.dto.UserDTO;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch,UserDTO> {

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
        return userDTO.getPassword().equals(userDTO.getPasswordConfirmation());
    }

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {

    }
}
