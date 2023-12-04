package com.crowdproj.vote.repo.postgresql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/vote",
    val user: String = "postgres",
    val password: String = "postgres",
    val schema: String = "vote",
    val table: String = "votes",
    val dropDatabase: Boolean = false,
//    val fastMigration: Boolean = true,
)
