package com.unity.messagingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


import com.unity.messagingservice.constant.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.unity.messagingservice.web.controller.validator.ValidUnixTimeStamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotNull;

import javax.validation.Valid;
import java.util.Map;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class MessageDto
{
	@NotNull
	@NotBlank
	@ValidUnixTimeStamp
	private String ts;
	@NotNull
	private String sender;
	@NotNull
	private Map<String, String> message;
	@JsonProperty("sent-from-ip")
	@Pattern(regexp = Constants.IPV4_PATTERN_SHORTEST)
	private String sentFromIp;
	private int priority;
}
