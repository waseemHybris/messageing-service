package com.unity.messagingservice.service.convertor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unity.messagingservice.dto.MessageDto;
import com.unity.messagingservice.persistence.entity.UnityMessage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageDtoToUnityMessageConvertor implements Converter<MessageDto, UnityMessage>
{
	private final ObjectMapper objectMapper;

	public MessageDtoToUnityMessageConvertor(final ObjectMapper objectMapper)
	{
		this.objectMapper = objectMapper;
	}

	@Override
	public UnityMessage convert(final MessageDto source)
	{
		final ObjectNode messagePayload = objectMapper.valueToTree(source.getMessage());

		 return UnityMessage.builder()
				.ts(source.getTs())
				.message(messagePayload)
				.priority(source.getPriority())
				.sender(source.getSender())
				.sendFromIp(source.getSentFromIp())
				//.id(source.get)
		.build();
	}
}
