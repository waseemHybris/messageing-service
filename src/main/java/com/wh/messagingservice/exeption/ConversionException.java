package com.wh.messagingservice.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConversionException extends RuntimeException
{

	public ConversionException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public ConversionException(final String message)
	{
		super(message);
	}
}