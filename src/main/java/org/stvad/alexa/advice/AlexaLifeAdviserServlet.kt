package org.stvad.alexa.advice

import com.amazon.ask.Skills
import com.amazon.ask.servlet.SkillServlet
import org.stvad.alexa.advice.handlers.ApartmentSearchInformationHandler
import org.stvad.alexa.advice.handlers.HelpApartmentSearchHandler
import org.stvad.alexa.advice.handlers.HelpIntentHandler
import org.stvad.alexa.advice.handlers.basicHandlers

class AlexaLifeAdviserServlet : SkillServlet(
        Skills.standard()
                .addRequestHandlers(
                        basicHandlers + listOf(
                                HelpApartmentSearchHandler(),
                                ApartmentSearchInformationHandler(),
                                HelpIntentHandler())).build())