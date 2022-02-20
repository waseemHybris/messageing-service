package com.unity.messagingservice.persistence.entity;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unity.messagingservice.persistence.entity.converter.MessagePayloadConvertor;
import com.unity.messagingservice.persistence.namespace.UnityMessageDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = UnityMessageDefinition.TABLE_NAME,schema =UnityMessageDefinition.SCHEMA_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnityMessage
{
	@Id
	@Column(name = UnityMessageDefinition.Column.ID)
	private String id;
	@Column(name = UnityMessageDefinition.Column.TENANT_ID)
	private String tenant_id;
	@Column(name = UnityMessageDefinition.Column.TS, nullable = false)
	private String ts;
	@Column(name = UnityMessageDefinition.Column.SENDER, nullable = false)
	private String sender;
//	@Column(name = UnityMessageDefinition.Column.MESSAGE, nullable = false)
//	@Convert(converter = MessagePayloadConvertor.class)
//	private ObjectNode message;
	@Column(name = UnityMessageDefinition.Column.SENT_FROM_IP)
	private String sendFromIp;
	@Column(name = UnityMessageDefinition.Column.PRIORITY)
	private Integer priority;
}
