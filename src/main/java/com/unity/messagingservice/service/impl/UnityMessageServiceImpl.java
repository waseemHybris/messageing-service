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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

@Slf4j
@Service
public class UnityMessageServiceImpl implements UnityMessageService
{
	private final EntityManager entityManager;
	private final MessageDtoToUnityMessageConvertor conversionService;
	private final MessageRepository messageRepository;

	public UnityMessageServiceImpl(final EntityManager entityManager, final MessageDtoToUnityMessageConvertor conversionService,
			final MessageRepository messageRepository)
	{
		this.entityManager = entityManager;
		this.conversionService = conversionService;
		this.messageRepository = messageRepository;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String processMessage(final String tenant, final MessageDto messageDto)
	{
		entityManager.setProperty("eclipselink.tenant-id", tenant);
		final var uuid = UUID.randomUUID().toString();

		final var entity = conversionService.convert(tenant, messageDto);
		entity.setId(uuid);
		messageRepository.save(entity);
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
