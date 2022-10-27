package `in`.lj.lifecare.data

import java.io.Serializable

data class User(
    val name: String = "User",
    val email: String = "gmail.com",
    val number: String = "7338787353"
): Serializable
