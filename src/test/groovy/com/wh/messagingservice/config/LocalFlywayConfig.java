/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */

package com.wh.messagingservice.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * This configuration cleans up the database and the execute the flyway migration for dev environment.
 * In order to have same behavior of dropping and creating the db schema for 'dev' profile
 */
@Configuration
@Profile({"dev"})
public class LocalFlywayConfig
{
	@Bean
	public FlywayMigrationStrategy flywayMigrationStrategy()
	{
		return flyway ->
		{
			flyway.clean();
			flyway.migrate();
		};
	}
}
