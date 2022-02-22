package com.unity.messagingservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unity.messagingservice.dto.MessageDto;
import com.unity.messagingservice.persistence.entity.UnityMessage;
import com.unity.messagingservice.repository.MessageRepository;
import com.unity.messagingservice.service.convertor.MessageDtoToUnityMessageConvertor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class UnityMessageServiceImplTest
{
	@InjectMocks
	private UnityMessageServiceImpl unityMessageService;

	@Mock
	private MessageDtoToUnityMessageConvertor conversionService;
	@Mock
	private MessageRepository messageRepository;
	@Mock
	private KafkaTemplate<String, MessageDto> unityMessageKafkaTemplate;
	@Mock
	private MessageDto messageDto;
	@Mock
	private UnityMessage unityMessage;

	private static String tenant = "tenant";


	@Test
	void processMessage() throws JsonProcessingException
	{
		Mockito.when(conversionService.convert(tenant, messageDto)).thenReturn(unityMessage);
		unityMessageService.processMessage(tenant, messageDto);
		Mockito.verify(messageRepository).save(Mockito.any());
		Mockito.verify(unityMessageKafkaTemplate).send(null, messageDto);
	}
}