package com.unity.messagingservice.service;

import com.unity.messagingservice.dto.MessageDto;
import com.unity.messagingservice.dto.MessageResponseDto;
import com.unity.messagingservice.dto.MessagingQueryObject;
import groovy.util.MapEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnityMessageService
{

	/**
	 * persist the messageDto {@link MessageDto} & publish a message to Kafla topic //TODO (transactionally?) .
	 * @param tenant
	 * @param messageDto
	 * @return
	 */
	String processMessage(final String tenant, final MessageDto messageDto);

	Page<MessageResponseDto> getAll(final String tenant, final MessagingQueryObject messagingQueryObject, final Pageable pageable);

}
