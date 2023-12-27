package com.crowdproj.vote.biz

import com.crowdproj.vote.biz.general.initRepo
import com.crowdproj.vote.biz.general.operation
import com.crowdproj.vote.biz.general.prepareResult
import com.crowdproj.vote.biz.general.stubs
import com.crowdproj.vote.biz.repo.repoCreate
import com.crowdproj.vote.biz.repo.repoDelete
import com.crowdproj.vote.biz.repo.repoPrepareCreate
import com.crowdproj.vote.biz.repo.repoPrepareDelete
import com.crowdproj.vote.biz.repo.repoPrepareUpdate
import com.crowdproj.vote.biz.repo.repoRead
import com.crowdproj.vote.biz.repo.repoUpdate
import com.crowdproj.vote.biz.validation.finishVoteValidation
import com.crowdproj.vote.biz.validation.validateIdNotEmpty
import com.crowdproj.vote.biz.validation.validateIdProperFormat
import com.crowdproj.vote.biz.validation.validateLockNotEmpty
import com.crowdproj.vote.biz.validation.validateRatingIdHasContent
import com.crowdproj.vote.biz.validation.validateRatingIdNotEmpty
import com.crowdproj.vote.biz.validation.validateScoreHasContent
import com.crowdproj.vote.biz.validation.validateScoreNotEmpty
import com.crowdproj.vote.biz.validation.validateUserIdHasContent
import com.crowdproj.vote.biz.validation.validateUserIdNotEmpty
import com.crowdproj.vote.biz.validation.validation
import com.crowdproj.vote.biz.workers.initStatus
import com.crowdproj.vote.biz.workers.stubCreateSuccess
import com.crowdproj.vote.biz.workers.stubDeleteSuccess
import com.crowdproj.vote.biz.workers.stubReadSuccess
import com.crowdproj.vote.biz.workers.stubValidationBadId
import com.crowdproj.vote.biz.workers.stubValidationBadRatingId
import com.crowdproj.vote.biz.workers.stubValidationBadScore
import com.crowdproj.vote.biz.workers.stubValidationBadUserId
import com.crowdproj.vote.common.CwpVoteContext
import com.crowdproj.vote.common.config.CwpVoteCorSettings
import com.crowdproj.vote.common.models.CwpVoteCommand
import com.crowdproj.vote.common.models.CwpVoteId
import com.crowdproj.vote.common.models.CwpVoteRatingId
import com.crowdproj.vote.common.models.CwpVoteScore
import com.crowdproj.vote.common.models.CwpVoteState
import com.crowdproj.vote.common.models.CwpVoteUserId
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.kotlin.cor.rootChain
import workers.stubNoCase

class CwpVoteProcessor(
    @Suppress("unused")
    private val corSettings: CwpVoteCorSettings = CwpVoteCorSettings()
) {

    suspend fun exec(ctx: CwpVoteContext) =
        BusinessChain.exec(ctx.apply { settings = this@CwpVoteProcessor.corSettings })

    companion object {
        private val BusinessChain = rootChain<CwpVoteContext> {
            initStatus("Init status")
            initRepo("Init repo")

            operation("Create vote", CwpVoteCommand.CREATE) {
                stubs("Stubs processing") {
                    stubCreateSuccess("Simulation of successful processing")
                    stubValidationBadRatingId("Simulating a vote identifier validation error")
                    stubValidationBadUserId("Simulating a user ID validation error")
                    stubValidationBadScore("Simulating a vote value validation error")
                    stubNoCase("Error: The requested stub is invalid")
                }
                validation {
                    worker("Copy data to voteValidating") { voteValidating = voteRequest.copy() }
                    worker("Clear id") { voteValidating.id = CwpVoteId.NONE }
                    worker("Clear score field") {
                        voteValidating.score =
                            CwpVoteScore(voteValidating.score.asString().trim())
                    }
                    worker("Clear ratingId field") {
                        voteValidating.ratingId = CwpVoteRatingId(voteValidating.ratingId.asString().trim())
                    }
                    worker("Clear userId field") {
                        voteValidating.userId =
                            CwpVoteUserId(voteValidating.userId.asString().trim())
                    }
                    validateRatingIdNotEmpty("Check that the rating identifier is not empty")
                    validateRatingIdHasContent("Checking characters in rating identifier")
                    validateUserIdNotEmpty("Check that the user ID is not empty")
                    validateUserIdHasContent("Checking characters in user ID")
                    validateScoreNotEmpty("Checking that the score is not empty")
                    validateScoreHasContent("Score character check")
                    finishVoteValidation("Checks is completed")
                }
                chain {
                    title = "Saving logic"
                    repoPrepareCreate("Prepare object to save")
                    repoCreate("Create vote into database")
                }
                prepareResult("Prepare answer")
            }
            operation("Read vote from database ", CwpVoteCommand.READ) {
                stubs("Stubs processing") {
                    stubReadSuccess("Simulation of successful processing")
                    stubValidationBadId("Simulating a vote identifier validation error")
                    stubNoCase("Error: The requested stub is invalid")
                }
                validation {
                    worker("Copy fields into adValidating") { voteValidating = voteRequest.copy() }
                    validateIdNotEmpty("Check that id is not empty")
                    validateIdProperFormat("Checking characters in ID")

                    finishVoteValidation("Checks is completed")
                }
                chain {
                    title = "Reading logic"
                    repoRead("Read vote from database")
                    worker {
                        title = "Preparing the object for Read"
                        on { state == CwpVoteState.RUNNING }
                        handle { voteRepoDone = voteRepoRead }
                    }
                }
                prepareResult("Preparing the object")
            }
            operation("Update vote", CwpVoteCommand.UPDATE) {
                stubs("Stubs processing") {
                    stubReadSuccess("Simulation of successful processing")
                    stubValidationBadId("Simulating a vote identifier validation error")
                    stubValidationBadRatingId("Simulating a vote identifier validation error")
                    stubValidationBadUserId("Simulating a user ID validation error")
                    stubValidationBadScore("Simulating a vote value validation error")
                    stubNoCase("Error: The requested stub is invalid")
                }
                validation {
                    worker("Copy fields into adValidating") { voteValidating = voteRequest.copy() }
                    validateIdNotEmpty("Check that id is not empty")
                    validateIdProperFormat("Checking characters in ID")
                    validateLockNotEmpty("Check that lock is not empty")
                    validateRatingIdNotEmpty("Check that the rating identifier is not empty")
                    validateRatingIdHasContent("Checking characters in rating identifier")
                    validateUserIdNotEmpty("Check that the user ID is not empty")
                    validateUserIdHasContent("Checking characters in user ID")
                    validateScoreNotEmpty("Checking that the score is not empty")
                    validateScoreHasContent("Score character check")

                    finishVoteValidation("Checks is completed")
                }
                chain {
                    title = "Saving logic"
                    repoRead("Read vote from database")
                    repoPrepareUpdate("Prepare object to update")
                    repoUpdate("Update vote in database")
                }
                prepareResult("Preparing the object")
            }
            operation("Cancel vote", CwpVoteCommand.DELETE) {
                stubs("Stubs processing") {
                    stubDeleteSuccess("Simulation of successful processing")
                    stubValidationBadId("Simulating a vote identifier validation error")
                    stubNoCase("Error: The requested stub is invalid")
                }
                validation {
                    worker("Copy fields into adValidating") {
                        voteValidating = voteRequest.copy()
                    }
                    validateIdNotEmpty("Check that id is not empty")
                    validateIdProperFormat("Checking characters in ID")
                    validateLockNotEmpty("Check that lock is not empty")
                    finishVoteValidation("Checks is completed")
                }
                chain {
                    title = "Сancellation logic"
                    repoRead("Read vote from database")
                    repoPrepareDelete("Prepare object to cancel")
                    repoDelete("Cancel vote")
                }
                prepareResult("Preparing the object")
            }
        }.build()
    }
}
