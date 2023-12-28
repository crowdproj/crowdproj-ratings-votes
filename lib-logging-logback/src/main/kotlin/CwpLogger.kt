package com.crowdproj.vote.logging.logback

import ch.qos.logback.classic.Logger
import com.crowdproj.vote.logging.common.ICwpLogWrapper
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun voteLoggerLogback(logger: Logger): ICwpLogWrapper = CwpLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun voteLoggerLogback(clazz: KClass<*>): ICwpLogWrapper =
    voteLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)

@Suppress("unused")
fun voteLoggerLogback(loggerId: String): ICwpLogWrapper = voteLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
