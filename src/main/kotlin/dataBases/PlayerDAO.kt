package dataBases

import java.sql.Connection

class PlayerDAO(private val conect: Connection)
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
        private const val TRUNCATE_TABLE_USERS_SQL = "TRUNCATE TABLE Player"
        /*private const val CREATE_TABLE_USERS_SQL =
            "CREATE TABLE USERS (id  number(3) NOT NULL AUTO_INCREMENT,name varchar(120) NOT NULL,email varchar(220) NOT NULL,country varchar(120),PRIMARY KEY (id))"*/
        private const val INSERT_USERS_SQL = "INSERT INTO Player" + "  (name, nickname, email) VALUES " + " (?, ?, ?);"
        private const val SELECT_USER_BY_ID = "select id,name,nickname, email from Player where id =?"
        private const val SELECT_ALL_USERS = "select * from Player"
        private const val DELETE_USERS_SQL = "delete from Player where id = ?;"
        private const val UPDATE_USERS_SQL = "update Player set name = ?,email= ?, nickname =? where id = ?;"
    }
}