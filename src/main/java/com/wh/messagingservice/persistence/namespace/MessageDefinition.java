package com.wh.messagingservice.persistence.namespace;

import com.wh.messagingservice.persistence.entity.MessageEntity;

/**
 * Defines the name of the columns for the {@link MessageEntity} entity.
 */
public final class MessageDefinition
{
	public static final String TABLE_NAME = "message";
	public static final String SCHEMA_NAME = "message";

	private MessageDefinition()
	{
		//Do nothing
	}

	public static final class Column
	{
		public static final String ID = "id";
		public static final String TENANT_ID = "tenant_id";
		public static final String TS = "ts";
		public static final String SENDER = "sender";
		public static final String MESSAGE = "message";
		public static final String SENT_FROM_IP = "sent_from_ip";
		public static final String PRIORITY = "priority";

		private Column()
		{
			// empty
		}
	}
}
