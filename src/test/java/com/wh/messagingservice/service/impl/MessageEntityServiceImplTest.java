package com.wh.messagingservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wh.messagingservice.dto.MessageDto;
import com.wh.messagingservice.persistence.entity.MessageEntity;
import com.wh.messagingservice.repository.MessageRepository;
import com.wh.messagingservice.service.convertor.MessageDtoToMessageEntityConvertor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class MessageEntityServiceImplTest
{
	@InjectMocks
	private MessageServiceImpl messageService;

	@Mock
	private MessageDtoToMessageEntityConvertor conversionService;
	@Mock
	private MessageRepository messageRepository;
	@Mock
	private KafkaTemplate<String, MessageDto> messageKafkaTemplate;
	@Mock
	private MessageDto messageDto;
	@Mock
	private MessageEntity messageEntity;

	private static String tenant = "tenant";


	@Test
	void processMessage() throws JsonProcessingException
	{
		Mockito.when(conversionService.convert(tenant, messageDto)).thenReturn(messageEntity);
		messageService.processMessage(tenant, messageDto);
		Mockito.verify(messageRepository).save(Mockito.any());
		Mockito.verify(messageKafkaTemplate).send(null, messageDto);
	}
}