import com.crowdproj.vote.common.CwpVoteContext

class CwpVoteProcessor {
    suspend fun exec(ctx: CwpVoteContext) {
        ctx.voteResponse = ctx.voteRequest
    }
}
