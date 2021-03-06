package org.stvad.alexa.advice.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import com.github.debop.kodatimes.dayDuration
import com.github.debop.kodatimes.days
import org.joda.time.Duration
import org.stvad.alexa.advice.model.ApartmentSearchInformationIntent
import org.stvad.alexa.advice.util.ApartmentSearchTitle
import org.stvad.algorithm.optimalThreshold
import org.stvad.kask.duration
import org.stvad.kask.request.IntentRequestHandler
import org.stvad.kask.request.respond
import java.util.Optional

class ApartmentSearchInformationHandler : IntentRequestHandler<ApartmentSearchInformationIntent>(ApartmentSearchInformationIntent) {
    companion object {
        val minimalTotalSearchTime = 7.dayDuration()

        val shouldLeapResponse = """At this point you've already visited an optimal number of apartments,
            | given the total time you've allocated for search. You should rent the next apartment that seem
            | better then the ones you've seen before.""".trimMargin()
        val shouldLookResponse = """You should keep looking for the next %s days, and then go for the first
            | apartment that seems better then all the ones you've seen by that time.""".trimMargin()

        val searchPeriodNotLongEnough = """Your stated total search time is a bit short, I don't think you can form
            | a good idea about market conditions in that time.
        """.trimMargin()
    }

    override fun handle(input: HandlerInput, intent: ApartmentSearchInformationIntent): Optional<Response> {
        if (!input.dialogState.isCompleted) return input.delegateDialog() //todo

        val totalTime = intent.total_duration.value?.duration
        val spentTime = intent.spent_duration.value?.duration

        if (totalTime == null || spentTime == null) throw IllegalStateException("Required slot values are missing")

        val responseText = if (totalTime < minimalTotalSearchTime) searchPeriodNotLongEnough else
            determineNextAction(spentTime, totalTime)

        return input.respond {
            withSpeech(responseText)
            withSimpleCard(ApartmentSearchTitle, responseText)
        }
    }

    private fun determineNextAction(spentTime: Duration, totalTime: Duration): String {
        val threshold = optimalThreshold(totalTime)

        return if (spentTime > threshold) shouldLeapResponse else
            shouldLookResponse.format(daysLeft(threshold, spentTime))
    }

    private fun daysLeft(threshold: Duration, spentTime: Duration) =
            threshold.minus(spentTime).days()
}