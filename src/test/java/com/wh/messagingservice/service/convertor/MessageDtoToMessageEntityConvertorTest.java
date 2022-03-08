package com.wh.messagingservice.service.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wh.messagingservice.dto.MessageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MessageDtoToMessageEntityConvertorTest
{
	public static final String TS = "1645371809";
	public static final String SENDER = "sender";
	public static final String SENT_FROM_IP = "10.10.10.10";
	public static final Map<@NotNull @Size(min = 1) String, String> MESSAGE = Map.of("foo", "bar");
	@InjectMocks
	private MessageDtoToMessageEntityConvertor messageDtoToMessageEntityConvertor;
	@Mock
	private ObjectMapper mapper;

	@Test
	void should_convert() throws JsonProcessingException
	{
		Mockito.when(mapper.writeValueAsString(MESSAGE)).thenReturn("{foo:bar}");
		final MessageDto msg = new MessageDto(TS, SENDER, MESSAGE, SENT_FROM_IP, 4);
		var entity = messageDtoToMessageEntityConvertor.convert("tenant", msg);

		assertThat(entity.getTs()).isEqualTo(TS);
		assertThat(entity.getSender()).isEqualTo(SENDER);
		assertThat(entity.getSendFromIp()).isEqualTo(SENT_FROM_IP);
		assertThat(entity.getMessage()).isEqualTo("{foo:bar}");
	}
}