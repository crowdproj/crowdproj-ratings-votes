package com.crowdproj.vote.app.spring.config

import com.crowdproj.vote.repo.postgresql.SqlProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "sql")
class SqlPropertiesEx(
    @Value("sql.url")
    override val url: String,
    @Value("sql.user")
    override val user: String,
    @Value("sql.password")
    override val password: String,
    @Value("sql.schema")
    override val schema: String
) : SqlProperties(url, user, password, schema)
