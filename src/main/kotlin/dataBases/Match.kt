package dataBases

import java.sql.Date
import java.time.LocalDate


data class Match(val id: Int, val gameId: Int, val playerId: Int,
                 val win_loss:Int, val word:String, val score:Int,
                 val game_date: LocalDate)

data class MatchWithNames(
    val id: Int, val gameName: String, val playerName: String,
    val win_loss:Int, val word:String, val score:Int,
    val game_date: LocalDate)
