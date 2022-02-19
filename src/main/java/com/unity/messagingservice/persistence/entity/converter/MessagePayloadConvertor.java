package com.unity.messagingservice.persistence.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unity.messagingservice.exeption.ConversionException;
import org.postgresql.util.PGobject;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@Converter
public class MessagePayloadConvertor implements AttributeConverter<ObjectNode, PGobject>
{
	private static final ObjectMapper mapper = new ObjectMapper();

	public MessagePayloadConvertor()
	{
		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
	}

	@Override
	public PGobject convertToDatabaseColumn(final ObjectNode objectNode)
	{
		try
		{
			final String jsonString = (Objects.isNull(objectNode)) ? null : mapper.writeValueAsString(objectNode);
			final PGobject pgObject = new PGobject();
			pgObject.setType("jsonb");
			pgObject.setValue(jsonString);
			return pgObject;
		}
		catch (SQLException | JsonProcessingException e)
		{
			throw new ConversionException("Unable to convert message payload to object", e);
		}
	}

	@Override
	public ObjectNode convertToEntityAttribute(final PGobject pgObject)
	{
		if (Objects.isNull(pgObject))
		{
			return null;
		}

		try
		{
			return mapper.readValue(pgObject.getValue(), ObjectNode.class);
		}
		catch (IOException e)
		{
			throw new ConversionException("Unable to convert stored message payload to json string", e);
		}
	}
}