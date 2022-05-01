package dataBases

import java.sql.Date


data class Match(val id: Int, val gameId: Int, val playerId: Int,
                 val win_loss:Int, val word:String, val score:Int,
                 val game_date: Date)
