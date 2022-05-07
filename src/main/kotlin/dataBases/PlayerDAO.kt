package dataBases

import java.sql.Connection
import java.sql.SQLException

class PlayerDAO(private val connect: Connection)
{
    // En el companion object creamos todas las constantes.
    // Las constante definirán las plantillas de las sentencias que necesitamos para construir
    // los selects, inserts, deletes, updates.

    // En aquellos casos en donde necesitemos insertar un parametro, pondremos un ?
    // Luego lo sistituiremos llamando a métodos setX, donde X será (Int, String, ...)
    // dependiendo del tiempo de dato que corresponda.
    companion object
    {
        private const val SCHEMA = "default"
        private const val TABLE = "Player"
        private const val TRUNCATE_TABLE_PLAYER_SQL = "TRUNCATE TABLE Player"
        //Si queremos poder ejecutar las sentencia en cualquier SGBD, la creación de las tablas debería, probablemente,
        // fuera del código; las consultas son más "universales", pero la creación es más específica porque los tipos de datos
        // son diferentes en general de un SGBD a otro.

        /*private const val CREATE_TABLE_PLAYER_SQL =
            "CREATE TABLE Player (id  number(3) NOT NULL AUTO_INCREMENT,name varchar(120) NOT NULL,
            email varchar(220) NOT NULL,country varchar(120),PRIMARY KEY (id))"*/
        private const val INSERT_PLAYER_SQL = "INSERT INTO Player" + "  (name, nickname, email) VALUES " + " (?, ?, ?);"
        private const val SELECT_PLAYER_BY_ID = "SELECT id,name,nickname, email FROM Player WHERE id =?"
        private const val SELECT_ALL_PLAYERS = "SELECT * FROM Player"
        private const val DELETE_PLAYER_SQL = "DELETE FROM Player WHERE id = ?;"
        private const val UPDATE_PLAYER_SQL = "UPDATE Player SET name = ?,email= ?, nickname =? WHERE id = ?;"
    }

/*/    fun prepareTable()
    {
        val metadata = connect.metaData
        val result = metadata.getTables(null, null, TABLE, null)
        if (!result.next())
            connect.prepareStatement(CREATE_TABLE_PLAYER_SQL).executeUpdate()
        else
            connect.prepareStatement(TRUNCATE_TABLE_PLAYER_SQL).executeUpdate()
    }*/

/*    fun createTable()
    {
        connect.prepareStatement(CREATE_TABLE_PLAYER_SQL).executeUpdate()
    }*/


    fun truncateTable():Boolean
    {
        try {
            connect.prepareStatement(TRUNCATE_TABLE_PLAYER_SQL).executeUpdate()
            connect.commit()
        }
        catch (e: SQLException)
        {
            printSQLException(e)
            return false
        }
        return true

    }

    fun insertPlayer(player: Player):Boolean
    {
        try {
            connect.prepareStatement(INSERT_PLAYER_SQL).executeQuery()
            connect.commit()
        }
        catch (e: SQLException)
        {
            printSQLException(e)
            return false
        }
        return true
    }

    fun selectPlayerById(id: Int): Player?
    {
        var player: Player? = null
        // Step 1: Preparamos la Statement, asignado los valores a los indices
        //          en función de las ? que existen en la plantilla
        val prepStmnt=connect.prepareStatement(SELECT_PLAYER_BY_ID)
        prepStmnt.setInt(1,id)
        // Step 2: Ejecutamos la Sentencia
        val result=prepStmnt.executeQuery()
        // Step 3: Obtenemos el resultado
        // Como estamos buscando por id, si existe, sólo deberíamos obtener una fila
        while(result.next())
        {
            val id=result.getInt("id")
            val name=result.getString("name")
            val nickname=result.getString("nickname")
            val email=result.getString("email")
            player=Player(id,name,nickname,email)
        }
        return player
    }

    fun selectAllPlayers(): List<Player>
    {
        val players= mutableListOf<Player>()
        val result=connect.prepareStatement(SELECT_ALL_PLAYERS).executeQuery()
        while(result.next())
        {
            val id=result.getInt("id")
            val name=result.getString("name")
            val nickname=result.getString("nickname")
            val email=result.getString("email")
            players.add(Player(id,name,nickname,email))
        }
        return players
    }

    fun deletePlayerByID(id: Int):Boolean
    {
        try {
            val prepStmnt= connect.prepareStatement(DELETE_PLAYER_SQL)
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

    fun updatePlayer(player: Player):Boolean
    {
        var rowUpdated=false
        try {
            val prepStmnt= connect.prepareStatement(UPDATE_PLAYER_SQL)
            prepStmnt.setString(1,player.name)
            prepStmnt.setString(2,player.nickName)
            prepStmnt.setString(3,player.email)
            prepStmnt.setInt(4,player.id)
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

    private fun printSQLException(ex: SQLException) {
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