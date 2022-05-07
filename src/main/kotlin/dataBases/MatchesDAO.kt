package dataBases

import java.sql.Connection
import java.sql.Date
import java.sql.SQLException
import java.time.LocalDate

class MatchesDAO(private val connect: Connection, private val dbType: DBConnectType)
{
    companion object
    {
        private const val SCHEMA = "default"
        private const val TABLE = "Matches"
        private const val TRUNCATE_TABLE_MATCHES_SQL = "TRUNCATE TABLE Matches"
        /* private const val CREATE_TABLE_USERS_SQL =
             "CREATE TABLE USERS (id  number(3) NOT NULL AUTO_INCREMENT,name varchar(120) NOT NULL,email varchar(220) NOT NULL,country varchar(120),PRIMARY KEY (id))"*/
        private const val INSERT_MATCHES_SQL = "INSERT INTO Matches " +
                                               "(gameId, playerID, word,game_date,score,win_loss) VALUES " +
                                               "(?, ?, ? ,?, ?,?);"

        private const val SELECT_MATCHES_BY_ID = "SELECT * FROM Matches where id =?"
        private const val SELECT_ALL_MATCHESS = "SELECT Matches.id, Player.name as pname, Game.name as gname," +
                                                " win_loss, word, score, game_date " +
                                                " FROM Matches, Player, Game" +
                                                " WHERE Matches.playerId = Player.id AND Matches.gameId = Game.id"
        private const val DELETE_MATCHES_SQL = "DELETE from Matches where id = ?;"
        private const val UPDATE_MATCHES_SQL = "UPDATE Matches set  gameId= ?, playerId= ?, " +
                                               "word=?, game_date=?, score=?, " +
                                               "lastWord =?, win_loss=? where id = ?;"
    }

    fun truncateTable():Boolean
    {
        try {
            connect.prepareStatement(TRUNCATE_TABLE_MATCHES_SQL).executeUpdate()
            connect.commit()
        }
        catch (e: SQLException)
        {
            printSQLException(e)
            return false
        }
        return true

    }

    fun insertMatch(player: Player):Boolean
    {
        try {
            connect.prepareStatement(INSERT_MATCHES_SQL).executeQuery()
            connect.commit()
        }
        catch (e: SQLException)
        {
            printSQLException(e)
            return false
        }
        return true
    }

    fun selectMatchById(id: Int): MatchesWithNames?
    {
        var match: MatchesWithNames? = null
        // Step 1: Preparamos la Statement, asignado los valores a los indices
        //          en función de las ? que existen en la plantilla
        val prepStmnt=connect.prepareStatement(SELECT_MATCHES_BY_ID)
        prepStmnt.setInt(1,id)
        // Step 2: Ejecutamos la Sentencia
        val result=prepStmnt.executeQuery()
        // Step 3: Obtenemos el resultado
        // Como estamos buscando por id, si existe, sólo deberíamos obtener una fila
        while(result.next())
        {
            val id = result.getInt("id")
            val playerName = result.getString("pname")
            val gameName = result.getString("gname")
            val win_loss = result.getInt("win_loss")
            val word = result.getString("word")
            val score = result.getInt("score")
            val gameDate: LocalDate =
                when(dbType)
                {
                    DBConnectType.SQLITE -> LocalDate.parse(result.getString("game_date").toString())

                    else -> result.getDate("game_date").toLocalDate()
                }
            match = MatchesWithNames(id, playerName, gameName, win_loss, word, score,gameDate)
        }
        return match
    }

    fun selectAllPlayers(): List<MatchesWithNames>
    {
        val matches= mutableListOf<MatchesWithNames>()
        val result=connect.prepareStatement(SELECT_ALL_MATCHESS).executeQuery()

        while (result.next())
        {
            val id = result.getInt("id")
            val playerName = result.getString("pname")
            val gameName = result.getString("gname")
            val win_loss = result.getInt("win_loss")
            val word = result.getString("word")
            val score = result.getInt("score")

            val gameDate: LocalDate =
                when(dbType)
                {
                    DBConnectType.SQLITE -> LocalDate.parse(result.getString("game_date").toString())

                    else -> result.getDate("game_date").toLocalDate()
                }

            matches.add(MatchesWithNames(id, gameName, playerName, win_loss, word, score, gameDate))
        }

        return matches
    }

    fun deletePlayerByID(id: Int):Boolean
    {
        try {
            val prepStmnt= connect.prepareStatement(DELETE_MATCHES_SQL)
            prepStmnt.setInt(1,id)
            prepStmnt.executeUpdate()
            connect.commit()
        }
        catch (e: SQLException)
        {
            printSQLException(e)
            return false
        }
        return true
    }

    fun updateMatch(match: Matches):Boolean
    {
        var rowUpdated=false
        try {
            val prepStmnt= connect.prepareStatement(UPDATE_MATCHES_SQL)
            prepStmnt.setInt(1,match.gameId)
            prepStmnt.setInt(2,match.playerId)
            prepStmnt.setString(3,match.word)

            prepStmnt.setDate(4, Date.valueOf(match.game_date))
            rowUpdated=prepStmnt.executeUpdate()>0
            connect.commit()
        }
        catch (e: SQLException)
        {
            printSQLException(e)
            return rowUpdated
        }
        return rowUpdated
    }

    private fun printSQLException(ex: SQLException)
    {
        for (e in ex) {
            if (e is SQLException) {
                e.printStackTrace(System.err)
                System.err.println("SQLState: " + e.sqlState)
                System.err.println("Error Code: " + e.errorCode)
                System.err.println("Message: " + e.message)
                var t = ex.cause
                while (t != null) {
                    println("Cause: $t")
                    t = t.cause
                }
            }
        }
    }
}