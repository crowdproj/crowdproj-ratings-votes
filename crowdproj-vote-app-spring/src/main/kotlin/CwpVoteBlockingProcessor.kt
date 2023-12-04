package com.crowdproj.vote.app.spring

import com.crowdproj.vote.biz.CwpVoteProcessor
import com.crowdproj.vote.common.CwpVoteContext
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CwpVoteBlockingProcessor(@Qualifier("cwpVoteProcessor") cwpVoteProcessor: CwpVoteProcessor) {
    private val processor = cwpVoteProcessor

    fun exec(ctx: CwpVoteContext) = runBlocking { processor.exec(ctx) }
}
