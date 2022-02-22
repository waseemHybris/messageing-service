package com.unity.messagingservice.web.controller.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
class ValidUnixTimeStampValidatorTest
{
	@InjectMocks
	private ValidUnixTimeStampValidator validUnixTimeStampValidator;

	@Mock
	private ConstraintValidatorContext constraintValidatorContext;

	@Mock
	private ValidUnixTimeStamp validUnixTimeStamp;

	@Test
	void should_validate_unix_time_stamp()
	{
		assertThat(validUnixTimeStampValidator.isValid("1645375349", constraintValidatorContext)).isTrue();
	}

	@Test
	void should_not_validate_non_unix_time_stamp()
	{
		assertThat(validUnixTimeStampValidator.isValid("invalid", constraintValidatorContext)).isFalse();
	}

}