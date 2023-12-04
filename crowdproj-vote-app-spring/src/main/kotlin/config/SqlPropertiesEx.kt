package com.crowdproj.vote.app.spring.config

import com.crowdproj.vote.repo.postgresql.SqlProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("sql")
class SqlPropertiesEx
@ConstructorBinding
constructor(
    url: String,
    user: String,
    password: String,
    schema: String,
    table: String,
    dropDatabase: Boolean
) : SqlProperties(url, user, password, schema, table, dropDatabase)
