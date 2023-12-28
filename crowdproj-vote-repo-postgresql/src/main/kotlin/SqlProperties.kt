package com.crowdproj.vote.repo.postgresql

open class SqlProperties(
    open val url: String = "jdbc:postgresql://localhost:5432/vote",
    open val user: String = "postgres",
    open val password: String = "postgres",
    open val schema: String = "vote"
)
