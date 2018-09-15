package org.stvad.alexa.advice.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import org.stvad.alexa.advice.model.HelpIntent
import org.stvad.alexa.advice.util.SkillName
import org.stvad.alexa.advice.util.supportedAdviceAreas
import org.stvad.kask.request.BasicIntentRequestHandler
import org.stvad.kask.request.respond
import java.util.Optional

class HelpIntentHandler : BasicIntentRequestHandler(HelpIntent.name) {

    override fun handle(input: HandlerInput): Optional<Response> {
        val speechText = "I can give you advice based on insights from computer science. " +
                "I'm currently qualified to talk about following topics: ${supportedAdviceAreas.joinToString()}"
        return input.respond {
            withSpeech(speechText)
            withSimpleCard(SkillName, speechText)
            withReprompt(speechText)
        }
    }
}
