package org.stvad.alexa.advice.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Intent
import com.amazon.ask.model.Response
import com.github.debop.kodatimes.dayDuration
import com.github.debop.kodatimes.days
import com.github.debop.kodatimes.secondDuration
import com.github.debop.kodatimes.seconds
import org.joda.time.DateTime.now
import org.joda.time.Duration
import org.joda.time.Period.parse
import org.stvad.alexa.advice.util.ApartmentSearchTitle
import org.stvad.alexa.advice.util.Intents.ApartmentSearchInformation
import org.stvad.alexa.advice.util.Slots
import org.stvad.alexa.advice.util.Slots.SpentDuration
import org.stvad.alexa.advice.util.Slots.TotalDuration
import org.stvad.ask.IntentRequestHandler
import java.util.Optional

class ApartmentSearchInformationHandler : IntentRequestHandler(ApartmentSearchInformation.alexaName) {
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

    override fun handleSafely(input: HandlerInput): Optional<Response> {
        if (!input.dialogState.isCompleted) return input.delegateDialog() //todo

        val totalTime = input.intent.durationFromSlot(TotalDuration)

        val responseText = if (totalTime < minimalTotalSearchTime) searchPeriodNotLongEnough else
            determineNextAction(input, totalTime)

        return input.responseBuilder
                .withSpeech(responseText)
                .withSimpleCard(ApartmentSearchTitle, responseText)
                .build()
    }

    private fun determineNextAction(input: HandlerInput, totalTime: Duration): String {
        val spentTime = input.intent.durationFromSlot(SpentDuration)

        val threshold = optimalThreshold(totalTime)

        return if (spentTime > threshold) shouldLeapResponse else
            shouldLookResponse.format(daysLeft(threshold, spentTime))
    }

    private fun optimalThreshold(totalTime: Duration) =
            (totalTime.seconds() * 0.37).toLong().secondDuration()

    private fun Intent.durationFromSlot(slot: Slots): Duration = parse(requireSlot(slot).value).toDurationFrom(now())

    private fun daysLeft(threshold: Duration, spentTime: Duration) =
            threshold.minus(spentTime).days()
}