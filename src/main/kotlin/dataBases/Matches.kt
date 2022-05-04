package dataBases

import java.time.LocalDate


data class Matches(val id: Int, val gameId: Int, val playerId: Int,
                   val win_loss:Int, val word:String, val score:Int,
                   val game_date: LocalDate)

data class MatchesWithNames(
    val id: Int, val gameName: String, val playerName: String,
    val win_loss:Int, val word:String, val score:Int,
    val game_date: LocalDate)
