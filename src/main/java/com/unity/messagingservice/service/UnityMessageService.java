package com.unity.messagingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.unity.messagingservice.dto.MessageDto;
import com.unity.messagingservice.dto.MessageResponseDto;
import com.unity.messagingservice.dto.MessagingQueryObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnityMessageService
{
	/**
	 * store the messageDto {@link MessageDto}  in db & publish a message to Kafka topic in one transaction
	 *
	 * @param tenant     the tenant name
	 * @param messageDto message payload
	 * @return the message generated id
	 */
	String processMessage(final String tenant, final MessageDto messageDto) throws JsonProcessingException;

	Page<MessageResponseDto> getAll(final String tenant, final MessagingQueryObject messagingQueryObject, final Pageable pageable);

}
