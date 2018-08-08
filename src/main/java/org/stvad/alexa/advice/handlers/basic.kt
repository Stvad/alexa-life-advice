package org.stvad.alexa.advice.handlers

import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.SessionEndedRequest
import org.stvad.alexa.advice.util.Intents.Cancel
import org.stvad.alexa.advice.util.Intents.Fallback
import org.stvad.alexa.advice.util.Intents.Stop
import org.stvad.alexa.advice.util.SkillName
import org.stvad.ask.handle

val welcomeReprompt = """What area of your life do you need an advice about?
    | Currently I can only help you with the apartment search, but I'm constantly learning new things.""".trimMargin()

//todo distinguish first launch after enabling/ not and giving short/long version
val welcomeSpeech = """Welcome to the Alexa Life Advice Skill. This skill is designed to help you with everyday problems,
        |by using insights from the field of Computer Science.
        |It's inspired by the ideas in the book 'Algorithms to live by', written by Brian Christian and Tom Griffiths. """.trimMargin() +
        welcomeReprompt

val basicHandlers = listOf(
        handle(LaunchRequest::class) {
            it.responseBuilder
                    .withSpeech(welcomeSpeech)
                    .withSimpleCard(SkillName, welcomeSpeech)
                    .withReprompt(welcomeReprompt)
                    .build()
        },
        handle(Cancel.alexaName, Stop.alexaName) {
            val speechText = "Thank you for using Life Advice skill. Goodbye"
            it.responseBuilder.withSpeech(speechText).withSimpleCard(SkillName, speechText).build()
        },
        handle(Fallback.alexaName) {
            val speechText = "Sorry, I don't know that. You can try saying help!"
            it.responseBuilder
                    .withSpeech(speechText)
                    .withSimpleCard(SkillName, speechText)
                    .withReprompt(speechText)
                    .build()
        },
        handle(SessionEndedRequest::class) { it.responseBuilder.build() } // any cleanup logic goes here
)