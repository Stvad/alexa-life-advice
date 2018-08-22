package org.stvad.alexa.advice.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import org.stvad.alexa.advice.model.HelpApartmentSearchIntent
import org.stvad.alexa.advice.util.ApartmentSearchTitle
import org.stvad.kask.request.BasicIntentRequestHandler
import java.util.Optional

class HelpApartmentSearchHandler : BasicIntentRequestHandler(HelpApartmentSearchIntent.name) {
    companion object {
        const val requirementsText = "To formulate strategy for searching the accommodation I'll need to know: " +
                "the amount of time you are willing to spend searching, and how long have you been searching already."
    }

    override fun handle(input: HandlerInput): Optional<Response> =
            input.responseBuilder
                    .withSpeech(requirementsText)
                    .withSimpleCard(ApartmentSearchTitle, requirementsText)
                    .withReprompt("Please tell me how long do you plan to spend looking for the apartment.")
                    .build()
}
