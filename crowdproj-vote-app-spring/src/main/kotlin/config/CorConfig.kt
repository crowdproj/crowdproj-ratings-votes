package com.crowdproj.vote.app.spring.config

import com.crowdproj.vote.common.config.CwpVoteCorSettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CorConfig {

    @Bean
    fun corSettings(): CwpVoteCorSettings = CwpVoteCorSettings.NONE
}
