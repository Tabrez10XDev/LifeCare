package `in`.lj.lifecare.data

import java.io.Serializable

data class Doctor(
    val name: String = "Dr. Rajvel",
    val specialization: String = "MD in Gynecology",
    val location: String = "Salem",
    val experience: String = "9 Years",
    val availability: Boolean = true,
    val visitingHours: String = "00:00 - 00:00",
    val count: String = "0",
    val geoTag: ArrayList<String> = arrayListOf<String>("0.0000","0.0000")
): Serializable
