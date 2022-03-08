package com.wh.messagingservice.service.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wh.messagingservice.dto.MessageDto;
import com.wh.messagingservice.persistence.entity.MessageEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MessageDtoToMessageEntityConvertor
{
	private final ObjectMapper objectMapper;

	public MessageDtoToMessageEntityConvertor(final ObjectMapper objectMapper)
	{
		this.objectMapper = objectMapper;
	}

	public MessageEntity convert(final String tenant, final MessageDto source) throws JsonProcessingException
	{
		final String messageStringPayload = objectMapper.writeValueAsString(source.getMessage());

		var message = MessageEntity.builder()
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
