package net.azarquiel.nba.model

data class Player(
    val firstName: String = "",
    val lastName: String = "",
    val team: String = "",
    val position: String = "",
    val dateOfBirth: String = "",
    val headShotUrl: String = ""
)