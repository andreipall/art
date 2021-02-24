package com.andreipall.art.validator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsMatch {

    String message() default "Password & Confirm password do not match.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
