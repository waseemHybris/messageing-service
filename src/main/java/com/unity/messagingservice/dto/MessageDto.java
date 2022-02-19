package com.unity.messagingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


import com.unity.messagingservice.constant.Constants;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import java.util.Map;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto
{
	@NonNull
	private String ts;
	@NonNull
	private String sender;
	@NonNull
	private Map<String, String> message;
	@JsonProperty("sent-from-ip")
	@Pattern(regexp = Constants.IP4ADDRESS_REG_EXP)
	private String sentFromIp;
	private int priority;
}
