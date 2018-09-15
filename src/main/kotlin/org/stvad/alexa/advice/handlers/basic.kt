package org.stvad.alexa.advice.handlers

import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.SessionEndedRequest
import org.stvad.alexa.advice.model.CancelIntent
import org.stvad.alexa.advice.model.FallbackIntent
import org.stvad.alexa.advice.model.StopIntent
import org.stvad.alexa.advice.util.SkillName
import org.stvad.kask.request.respond

val welcomeReprompt = """What area of your life do you need an advice about?
    | Currently I can only help you with the apartment search, but I'm constantly learning new things.""".trimMargin()

//todo distinguish first launch after enabling/ not and giving short/long version
val welcomeSpeech = """Welcome to the Alexa Life Advice Skill. This skill is designed to help you with everyday problems,
        |by using insights from the field of Computer Science.
        |It's inspired by the ideas in the book 'Algorithms to live by', written by Brian Christian and Tom Griffiths. """.trimMargin() +
        welcomeReprompt

val basicHandlers = listOf(
        respond(LaunchRequest::class) {
            withSpeech(welcomeSpeech)
            withSimpleCard(SkillName, welcomeSpeech)
            withReprompt(welcomeReprompt)
        },
        respond(CancelIntent, StopIntent) {
            val speechText = "Thank you for using Life Advice skill. Goodbye"
            withSpeech(speechText)
            withSimpleCard(SkillName, speechText)
        },
        respond(FallbackIntent) {
            val speechText = "Sorry, I don't know that. You can try saying help!"
            withSpeech(speechText)
            withSimpleCard(SkillName, speechText)
            withReprompt(speechText)
        },
        respond(SessionEndedRequest::class) { } // any cleanup logic goes here
)