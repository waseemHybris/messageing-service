package com.wh.messagingservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wh.messagingservice.dto.MessageDto;
import com.wh.messagingservice.dto.MessageResponseDto;
import com.wh.messagingservice.dto.MessagingQueryObject;
import com.wh.messagingservice.service.MessageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/messages")
@Slf4j
public class MessageController
{

	public static final String QUERY_OBJECT_ATTRIBUTE = "queryObject";

	private MessageService messageService;

	public MessageController(final MessageService messageService)
	{
		this.messageService = messageService;
	}

	@PostMapping
	public HttpEntity<Void> create(@RequestHeader final String tenant, @RequestBody @Valid final MessageDto messageDto)
			throws JsonProcessingException
	{
		log.info("starting to create and publish message with payload: {}", messageDto.toString());
		messageService.processMessage(tenant, messageDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public HttpEntity<Page<MessageResponseDto>> getAll(@RequestHeader final String tenant,
			@PageableDefault(size = 20, direction = Sort.Direction.ASC) Pageable pageable,
			@ModelAttribute(value = QUERY_OBJECT_ATTRIBUTE, binding = false) final MessagingQueryObject queryObject)
	{
		log.info("starting to get messages}");
		var response = messageService.getAll(tenant, queryObject, pageable);
		return ResponseEntity.ok().body(response);
	}

	@ModelAttribute(value = QUERY_OBJECT_ATTRIBUTE)
	MessagingQueryObject buildQueryObject(@RequestParam(value = "ids", required = false) final String ids)
	{
		final List<String> idList = splitParameterByComma(ids);
		return MessagingQueryObject.builder().ids(idList).build();
	}

	/**
	 * This method generates a list of parameter values split by comma.
	 *
	 * @param paramValue the initial value of the parameter.
	 * @return List of elements split by comma or empty list
	 */
	public static List<String> splitParameterByComma(final String paramValue)
	{
		return new ArrayList<>(Optional.ofNullable(paramValue)
				.map(p -> Arrays.asList(StringUtils.commaDelimitedListToStringArray(StringUtils.uriDecode(p, StandardCharsets.UTF_8))))
				.orElse(Collections.emptyList()));
	}
}
