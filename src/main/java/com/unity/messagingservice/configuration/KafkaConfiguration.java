package com.unity.messagingservice.configuration;

import com.unity.messagingservice.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AutoConfigureAfter({ HibernateJpaAutoConfiguration.class, KafkaAutoConfiguration.class })
@Configuration
@EnableTransactionManagement
@Slf4j
public class KafkaConfiguration
{
	@Value(value = "${spring.kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value(value = "${spring.kafka.topicName}")
	private String topicName;

	@Bean
	public KafkaAdmin kafkaAdmin()
	{
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic topic()
	{
		return new NewTopic(topicName, 1, (short) 1);
	}

	@Bean
	public ProducerFactory<String, MessageDto> unityMessageProducerFactory()
	{
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "tx");
		configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1000);

		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean("unityMessageKafkaTemplate")
	KafkaTemplate<String, MessageDto> standaloneKafkaTemplate(ProducerFactory unityMessageProducerFactory)
	{
		KafkaTemplate<String, MessageDto> kafkaTemplate = new KafkaTemplate(unityMessageProducerFactory);
		kafkaTemplate.setTransactionIdPrefix(String.format("tx-%s", UUID.randomUUID()));

		return kafkaTemplate;
	}

}
