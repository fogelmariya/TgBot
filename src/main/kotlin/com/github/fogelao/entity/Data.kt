package com.github.fogelao.entity

/**
 * @author Fogel Artem on 27.05.2018.
 */
class Data(
        val trackCreationDateTime: String,
        val trackUpdateDateTime: String,
        val fromCountry: String,
        val destinationCountry: String,
        val destinationName: String,
        val events: List<Event>,
        val lastPoint: Event
) {
    override fun toString(): String {
        return "TrackCreationDateTime: $trackCreationDateTime\n" +
                "FromCountry: $fromCountry\n" +
                "DestinationCountry: $destinationCountry\n" +
                "DestinationName: $destinationName"
    }
}