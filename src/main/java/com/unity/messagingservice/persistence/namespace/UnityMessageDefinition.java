package com.unity.messagingservice.persistence.namespace;

/**
 * Defines the name of the columns for the {@link com.unity.messagingservice.persistence.entity.UnityMessage} entity.
 */
public final class UnityMessageDefinition
{
	public static final String TABLE_NAME = "unitymessage";
	public static final String SCHEMA_NAME = "unitymessage";

	private UnityMessageDefinition()
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
