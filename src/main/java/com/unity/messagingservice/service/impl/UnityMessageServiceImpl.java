package com.unity.messagingservice.service.impl;

import com.unity.messagingservice.dto.MessageDto;
import com.unity.messagingservice.dto.MessageResponseDto;
import com.unity.messagingservice.dto.MessagingQueryObject;
import com.unity.messagingservice.repository.MessageRepository;
import com.unity.messagingservice.service.UnityMessageService;
import com.unity.messagingservice.service.convertor.MessageDtoToUnityMessageConvertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class UnityMessageServiceImpl implements UnityMessageService
{
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
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String processMessage(final String tenant, final MessageDto messageDto)
	{
		final var entity = conversionService.convert(tenant, messageDto);
		entity.setId(UUID.randomUUID().toString());
		messageRepository.save(entity);
		// if an exception happens when sending the kafka message to the topic, the DB transaction will be rolled back.
		unityMessageKafkaTemplate.send("unityMessageQueue", messageDto);
		// there is a small risk here that kafka msg is successfully published to the topic but the DB transaction fails and rolled back.
		return entity.getId();
	}

	@Override
	public Page<MessageResponseDto> getAll(final String tenant, final MessagingQueryObject messagingQueryObject,
			final Pageable pageable)
	{
		//TODO implement and test me
		return null;
	}

}
