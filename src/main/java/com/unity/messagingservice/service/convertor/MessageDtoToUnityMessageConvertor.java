package com.unity.messagingservice.service.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unity.messagingservice.dto.MessageDto;
import com.unity.messagingservice.persistence.entity.UnityMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MessageDtoToUnityMessageConvertor
{
	private final ObjectMapper objectMapper;

	public MessageDtoToUnityMessageConvertor(final ObjectMapper objectMapper)
	{
		this.objectMapper = objectMapper;
	}

	public UnityMessage convert(final String tenant, final MessageDto source) throws JsonProcessingException
	{
		final String messageStringPayload = objectMapper.writeValueAsString(source.getMessage());

		var message = UnityMessage.builder()
				.ts(source.getTs())
				.message(messageStringPayload)
				.sender(source.getSender())
				.tenant_id(tenant)
				.build();

		if (Objects.nonNull(source.getSentFromIp()))
		{
			message.setSendFromIp(source.getSentFromIp());
		}
		if (Objects.nonNull(source.getPriority()))
		{
			message.setPriority(source.getPriority());
		}
		return message;
	}
}
