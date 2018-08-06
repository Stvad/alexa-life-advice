package org.stvad.alexa.advice.util

const val SkillName = "Alexa Life Advice"
const val ApartmentSearchTitle = "Apartment Search"

enum class Intents(val alexaName: String) { //todo consider autogenerating this based on the model
    //todo also consider overriding toString and adjusting the handlers accordingly
    HelpApartmentSearch("HelpApartmentSearchIntent"),
    ApartmentSearchInformation("ApartmentSearchInformationIntent"),

    Cancel("AMAZON.CancelIntent"),
    Stop("AMAZON.StopIntent"),
    Help("AMAZON.HelpIntent")

}

enum class Slots(val alexaName: String, val type: String) {
    TotalDuration("total_duration", "AMAZON.DURATION"),
    SpentDuration("spent_duration", "AMAZON.DURATION");

    override fun toString(): String {
        return alexaName
    }
}