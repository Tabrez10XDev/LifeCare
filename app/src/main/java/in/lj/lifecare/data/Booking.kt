package `in`.lj.lifecare.data

import java.io.Serializable

data class Booking(
    val doctorName: String = "Tabrez",
    val doctorDept: String = "Cardiologist",
    val day: String = "Sat",
    val date: String = "7.30 AM - 7.30 PM",
    val status: Boolean = true,
    val visitingHours: String = "7 AM - 11 AM",
    val ddmmyy: String = "01/01/2022"
): Serializable
