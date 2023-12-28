package com.crowdproj.vote.app.spring.config

import com.crowdproj.vote.biz.CwpVoteProcessor
import com.crowdproj.vote.common.config.CwpVoteCorSettings
import com.crowdproj.vote.common.repo.IVoteRepository
import com.crowdproj.vote.logging.common.CwpLoggerProvider
import com.crowdproj.vote.logging.logback.voteLoggerLogback
import com.crowdproj.vote.repo.postgresql.RepoVoteSQL
import com.crowdproj.vote.repo.postgresql.SqlProperties
import com.crowdproj.vote.repoinmemory.VoteRepoInMemory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(SqlPropertiesEx::class)
class CorConfig {

    @Bean
    fun loggerProvider(): CwpLoggerProvider = CwpLoggerProvider { voteLoggerLogback(it) }

    @Bean
    fun prodRepository(sqlProperties: SqlProperties) = RepoVoteSQL(sqlProperties)

    @Bean
    fun testRepository() = VoteRepoInMemory()

    @Bean
    fun corSettings(
        @Qualifier("prodRepository") prodRepository: IVoteRepository,
        @Qualifier("testRepository") testRepository: IVoteRepository
    ): CwpVoteCorSettings = CwpVoteCorSettings(
        loggerProvider = loggerProvider(),
        repoProd = prodRepository,
        repoTest = testRepository
    )

    @Bean
    fun cwpVoteProcessor(@Qualifier("corSettings") corSettings: CwpVoteCorSettings) = CwpVoteProcessor(corSettings = corSettings)
}
