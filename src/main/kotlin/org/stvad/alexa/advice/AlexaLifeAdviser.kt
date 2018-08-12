package org.stvad.alexa.advice

import com.amazon.ask.Skill
import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import com.amazon.ask.servlet.SkillServlet
import org.stvad.alexa.advice.handlers.ApartmentSearchInformationHandler
import org.stvad.alexa.advice.handlers.HelpApartmentSearchHandler
import org.stvad.alexa.advice.handlers.HelpIntentHandler
import org.stvad.alexa.advice.handlers.basicHandlers

val alexaLifeAdviceSkill: Skill = Skills.standard().addRequestHandlers(
        basicHandlers + listOf(
                HelpApartmentSearchHandler(),
                ApartmentSearchInformationHandler(),
                HelpIntentHandler())).build()

class AlexaLifeAdviserServlet : SkillServlet(alexaLifeAdviceSkill)
class AlexaLifeAdviserStreamHandler : SkillStreamHandler(alexaLifeAdviceSkill)