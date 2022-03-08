package com.wh.messagingservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wh.messagingservice.dto.MessageDto;
import com.wh.messagingservice.dto.MessageResponseDto;
import com.wh.messagingservice.dto.MessagingQueryObject;
import com.wh.messagingservice.persistence.entity.MessageEntity;
import com.wh.messagingservice.repository.MessageRepository;
import com.wh.messagingservice.service.MessageService;
import com.wh.messagingservice.service.convertor.MessageDtoToMessageEntityConvertor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService
{
	@Value(value = "${spring.kafka.topicName}")
	private String topicName;

	private final MessageDtoToMessageEntityConvertor conversionService;
	private final MessageRepository messageRepository;
	private final KafkaTemplate<String, MessageDto> messageKafkaTemplate;

	public MessageServiceImpl(final MessageDtoToMessageEntityConvertor conversionService,
			final MessageRepository messageRepository, final KafkaTemplate<String, MessageDto> messageKafkaTemplate)
	{
		this.conversionService = conversionService;
		this.messageRepository = messageRepository;
		this.messageKafkaTemplate = messageKafkaTemplate;
	}

	@Override
	@Transactional(value = "transactionManager")
	public String processMessage(final String tenant, final MessageDto messageDto) throws JsonProcessingException
	{
		final var entity = persistMessage(tenant, messageDto);
		publishToTopic(messageDto);
		return entity.getId();
	}

	@Override
	public Page<MessageResponseDto> getAll(final String tenant, final MessagingQueryObject messagingQueryObject,
			final Pageable pageable)
	{
		//TODO implement and test me
		return null;
	}

	private void publishToTopic(final MessageDto messageDto)
	{
		messageKafkaTemplate.send(topicName, messageDto);
	}

	private MessageEntity persistMessage(final String tenant, final MessageDto messageDto) throws JsonProcessingException
	{
		final var entity = conversionService.convert(tenant, messageDto);
		entity.setId(UUID.randomUUID().toString());
		messageRepository.save(entity);
		return entity;
	}
}
