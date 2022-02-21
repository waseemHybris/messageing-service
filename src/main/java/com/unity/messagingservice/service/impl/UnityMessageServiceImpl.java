package com.unity.messagingservice.service.impl;

import com.unity.messagingservice.dto.MessageDto;
import com.unity.messagingservice.dto.MessageResponseDto;
import com.unity.messagingservice.dto.MessagingQueryObject;
import com.unity.messagingservice.persistence.entity.UnityMessage;
import com.unity.messagingservice.repository.MessageRepository;
import com.unity.messagingservice.service.UnityMessageService;
import com.unity.messagingservice.service.convertor.MessageDtoToUnityMessageConvertor;
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
public class UnityMessageServiceImpl implements UnityMessageService
{
	@Value(value = "${spring.kafka.topicName}")
	private String topicName;

	private final MessageDtoToUnityMessageConvertor conversionService;
	private final MessageRepository messageRepository;
	private final KafkaTemplate<String, MessageDto> unityMessageKafkaTemplate;

	public UnityMessageServiceImpl(final MessageDtoToUnityMessageConvertor conversionService,
			final MessageRepository messageRepository, final KafkaTemplate<String, MessageDto> unityMessageKafkaTemplate)
	{
		this.conversionService = conversionService;
		this.messageRepository = messageRepository;
		this.unityMessageKafkaTemplate = unityMessageKafkaTemplate;
	}

	@Override
	@Transactional(value = "transactionManager")
	public String processMessage(final String tenant, final MessageDto messageDto)
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
		unityMessageKafkaTemplate.send(topicName, messageDto);
	}

	private UnityMessage persistMessage(final String tenant, final MessageDto messageDto)
	{
		final var entity = conversionService.convert(tenant, messageDto);
		entity.setId(UUID.randomUUID().toString());
		messageRepository.save(entity);
		return entity;
	}

}
