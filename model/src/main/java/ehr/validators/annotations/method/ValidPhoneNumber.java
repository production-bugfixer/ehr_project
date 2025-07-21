package ehr.validators.annotations.method;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {
    String message() default "Phone number must start with 9, 8, or 7 and be 10 digits long";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}