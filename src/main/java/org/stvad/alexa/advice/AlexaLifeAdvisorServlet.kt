/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package org.stvad.alexa.advice

import com.amazon.ask.Skills
import org.stvad.alexa.advice.handlers.CancelandStopIntentHandler
import org.stvad.alexa.advice.handlers.HelpIntentHandler
import org.stvad.alexa.advice.handlers.SessionEndedRequestHandler
import org.stvad.alexa.advice.handlers.helloWorldHandler
import org.stvad.alexa.advice.handlers.launchRequestHandler
import com.amazon.ask.servlet.SkillServlet
import org.stvad.ask.handle

// Add your skill id below
//.withSkillId("")
class AlexaLifeAdvisorServlet : SkillServlet(
        Skills.standard()
                .addRequestHandlers(
                        CancelandStopIntentHandler(),
                        handle("HelpApartmentSearchIntent") { it.responseBuilder.withSpeech("Wheeeeee").build() },
                        helloWorldHandler,
                        HelpIntentHandler(),
                        launchRequestHandler,
                        SessionEndedRequestHandler())
                .build())
