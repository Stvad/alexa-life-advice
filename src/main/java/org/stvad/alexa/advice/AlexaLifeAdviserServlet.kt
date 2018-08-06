package org.stvad.alexa.advice

import com.amazon.ask.Skills
import com.amazon.ask.servlet.SkillServlet
import org.stvad.alexa.advice.handlers.ApartmentSearchInformationHandler
import org.stvad.alexa.advice.handlers.CancelandStopIntentHandler
import org.stvad.alexa.advice.handlers.FallbackIntentHandler
import org.stvad.alexa.advice.handlers.HelpApartmentSearchHandler
import org.stvad.alexa.advice.handlers.HelpIntentHandler
import org.stvad.alexa.advice.handlers.SessionEndedRequestHandler
import org.stvad.alexa.advice.handlers.helloWorldHandler
import org.stvad.alexa.advice.handlers.launchRequestHandler

class AlexaLifeAdviserServlet : SkillServlet(
        Skills.standard()
                .addRequestHandlers(
                        CancelandStopIntentHandler(),
                        HelpApartmentSearchHandler(),
                        ApartmentSearchInformationHandler(),
                        helloWorldHandler,
                        HelpIntentHandler(),
                        launchRequestHandler,
                        SessionEndedRequestHandler(),
                        FallbackIntentHandler())
                .build())