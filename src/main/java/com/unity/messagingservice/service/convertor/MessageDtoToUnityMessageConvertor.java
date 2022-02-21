package com.unity.messagingservice.service.convertor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unity.messagingservice.dto.MessageDto;
import com.unity.messagingservice.persistence.entity.UnityMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageDtoToUnityMessageConvertor
{
	private final ObjectMapper objectMapper;

	public MessageDtoToUnityMessageConvertor(final ObjectMapper objectMapper)
	{
		this.objectMapper = objectMapper;
	}

	public UnityMessage convert(final String tenant, final MessageDto source)
	{
		final ObjectNode messagePayload = objectMapper.valueToTree(source.getMessage());

		return UnityMessage.builder()
				.ts(source.getTs())
				//.message(messagePayload)
				.priority(source.getPriority())
				.sender(source.getSender())
				.sendFromIp(source.getSentFromIp())
				.tenant_id(tenant)
				.build();
	}
}
