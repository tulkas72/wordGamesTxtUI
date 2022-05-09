package dataBases

import java.sql.Connection
import java.sql.SQLException


class GameDAO(private val connect: Connection)
{

    companion object
    {
        private const val SCHEMA = "default"
        private const val TABLE = "Game"
        private const val TRUNCATE_TABLE_GAME_SQL = "TRUNCATE TABLE Game"
       /* private const val CREATE_TABLE_USERS_SQL =
            "CREATE TABLE USERS (id  number(3) NOT NULL AUTO_INCREMENT,name varchar(120) NOT NULL,email varchar(220) NOT NULL,country varchar(120),PRIMARY KEY (id))"*/
        private const val INSERT_GAME_SQL = "INSERT INTO Game" + "  (name, rules, lastWord) VALUES " + " (?, ?, ?);"
        private const val SELECT_GAME_BY_ID = "SELECT id,name,rules, lastWord FROM Game where id =?"
        private const val SELECT_ALL_GAMES = "SELECT * from Game"
        private const val DELETE_GAME_SQL = "DELETE from Game where id = ?;"
        private const val UPDATE_GAME_SQL = "UPDATE Game set name = ?,rules= ?, lastWord =? where id = ?;"
    }

    fun truncateTable():Boolean
    {
        try {
            connect.prepareStatement(TRUNCATE_TABLE_GAME_SQL).executeUpdate()
            connect.commit()
        }
        catch (e: SQLException)
        {
            SQLUtils.printSQLException(e)
            return false
        }
        return true

    }

    fun insertPlayer(player: Player):Boolean
    {
        try {
            connect.prepareStatement(INSERT_GAME_SQL).executeQuery()
            connect.commit()
        }
        catch (e: SQLException)
        {
            SQLUtils.printSQLException(e)
            return false
        }
        return true
    }

    fun selectPlayerById(id: Int): Player?
    {
        var player: Player? = null
        // Step 1: Preparamos la Statement, asignado los valores a los indices
        //          en función de las ? que existen en la plantilla
        val prepStmnt=connect.prepareStatement(SELECT_GAME_BY_ID)
        prepStmnt.setInt(1,id)
        // Step 2: Ejecutamos la Sentencia
        val result=prepStmnt.executeQuery()
        // Step 3: Obtenemos el resultado
        // Como estamos buscando por id, si existe, sólo deberíamos obtener una fila
        while(result.next())
        {
            val id=result.getInt("id")
            val name=result.getString("name")
            val nickname=result.getString("rules")
            val email=result.getString("lastWord")
            player=Player(id,name,nickname,email)
        }
        return player
    }

    fun selectAllPlayers(): List<Game>
    {
        val games= mutableListOf<Game>()
        val result=connect.prepareStatement(SELECT_ALL_GAMES).executeQuery()
        while(result.next())
        {
            val id=result.getInt("id")
            val name=result.getString("name")
            val rules=result.getString("rules")
            val lastWord=result.getString("lastWord")
            games.add(Game(id,name,rules,lastWord))
        }
        return games
    }

    fun deletePlayerByID(id: Int):Boolean
    {
        try {
            val prepStmnt= connect.prepareStatement(DELETE_GAME_SQL)
            prepStmnt.setInt(1,id)
            prepStmnt.executeUpdate()
            connect.commit()
        }
        catch (e: SQLException)
        {
            SQLUtils.printSQLException(e)
            return false
        }
        return true
    }

    fun updatePlayer(game: Game):Boolean
    {
        var rowUpdated=false
        try {
            val prepStmnt= connect.prepareStatement(UPDATE_GAME_SQL)
            prepStmnt.setString(1,game.name)
            prepStmnt.setString(2,game.rules)
            prepStmnt.setString(3,game.lastWord)
            prepStmnt.setInt(4,game.id)
            rowUpdated=prepStmnt.executeUpdate()>0
            connect.commit()
        }
        catch (e: SQLException)
        {
            SQLUtils.printSQLException(e)
            return rowUpdated
        }
        return rowUpdated
    }

}