package com.wh.messagingservice.web.controller.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.util.Date;

@Slf4j
public class ValidUnixTimeStampValidator implements ConstraintValidator<ValidUnixTimeStamp, String>
{
	@Override
	public void initialize(final ValidUnixTimeStamp constraintAnnotation)
	{
		// Nothing to do here
	}

	@Override
	public boolean isValid(final String timeStamp, final ConstraintValidatorContext constraintValidatorContext)
	{
		try
		{
			Instant instant = Instant.ofEpochSecond(Long.parseLong(timeStamp));
			Date date = Date.from(instant);
			log.info("ts is a valid Epoch Unix Timestamp and it translates to: {}", date);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	private void addViolationByMessage(final String message, final String propertyNode,
			final ConstraintValidatorContext constraintContext)
	{
		constraintContext.disableDefaultConstraintViolation();
		constraintContext.buildConstraintViolationWithTemplate(message).addPropertyNode(propertyNode).addConstraintViolation();
	}
}
