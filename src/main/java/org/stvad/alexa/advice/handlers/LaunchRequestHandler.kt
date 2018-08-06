package org.stvad.alexa.advice.handlers

import com.amazon.ask.model.LaunchRequest
import org.stvad.ask.handle

//todo distinguish first launch after enabling/ not and giving short/long version

val launchRequestHandler = handle(LaunchRequest::class) {
    val speechText = """Welcome to the Alexa Life Advice Skill. This skill is designed to help you with everyday problems,
        |by using insights from the field of Computer Science.
        |It's inspired by the ideas in the book 'Algorithms to live by', written by Brian Christian and Tom Griffiths.""".trimMargin()
    it.responseBuilder
            .withSpeech(speechText)
            .withSimpleCard("HelloWorld", speechText)
            .withReprompt(speechText)
            .build()
}
