package com.crowdproj.vote.common.exceptions

import com.crowdproj.vote.common.models.CwpVoteLock

class RepoConcurrencyException(expectedLock: CwpVoteLock, actualLock: CwpVoteLock?) : RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
