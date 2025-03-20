package com.example.superherohero.data

import com.example.superherohero.R
import com.google.gson.annotations.SerializedName

class SuperheroResponse (
    val response: String,
    val results: List<Superhero>
)

class Superhero (
    val id: String,
    val name: String,
    val biography: Biography,
    val work: Work,
    val appearance: Appearance,
    @SerializedName("powerstats") val stats: Stats,
    val image: Image
) {
    fun getAlignmentColor(): Int {
        return when (biography.alignment) {
            "good" -> R.color.alignment_color_good
            "bad" -> R.color.alignment_color_bad
            else -> R.color.alignment_color_neutral
        }
    }

    class Biography(
        val publisher: String,
        @SerializedName("full-name") val realName: String,
        @SerializedName("place-of-birth") val placeOfBirth: String,
        val alignment: String
    )

    class Work(
        val occupation: String,
        val base: String
    )

    class Appearance(
        val gender: String,
        val race: String,
        @SerializedName("eye-color") val eyeColor: String,
        @SerializedName("hair-color") val hairColor: String,
        val height: List<String>,
        val weight: List<String>,
    ) {
        fun getWeightKg(): String {
            return weight[1]
        }

        fun getHeightCm(): String {
            return height[1]
        }
    }

    class Stats(
        val intelligence: String,
        val strength: String,
        val speed: String,
        val durability: String,
        val power: String,
        val combat: String

    )

    class Image(val url: String)
}


