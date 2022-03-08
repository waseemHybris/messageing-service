package com.wh.messagingservice.persistence.entity;

import com.wh.messagingservice.persistence.namespace.MessageDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = MessageDefinition.TABLE_NAME, schema =MessageDefinition.SCHEMA_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity
{
	@Id
	@Column(name = MessageDefinition.Column.ID)
	private String id;
	@Column(name = MessageDefinition.Column.TENANT_ID)
	private String tenant_id;
	@Column(name = MessageDefinition.Column.TS, nullable = false)
	private String ts;
	@Column(name = MessageDefinition.Column.SENDER, nullable = false)
	private String sender;
	@Column(name = MessageDefinition.Column.MESSAGE, nullable = false)
	private String message;
	@Column(name = MessageDefinition.Column.SENT_FROM_IP)
	private String sendFromIp;
	@Column(name = MessageDefinition.Column.PRIORITY)
	private Integer priority;
}
