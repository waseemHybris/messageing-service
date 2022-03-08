package com.wh.messagingservice.web.controller.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
/**
 * Validates the ts is a valid unix time stamp
 */
@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidUnixTimeStampValidator.class)
public @interface ValidUnixTimeStamp
{
	String message() default "The time stamp is not valid.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
