package dataBases

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConnectionBuilder
    (private val connectionType: DBConnectType = DBConnectType.H2,
     private val dbName: String = "",    private val userName: String = "",
     private val password: String = ""
    )
{
    // TODO Auto-generated catch block
    lateinit var connection: Connection

    // La URL de conexión. Tendremos que cambiarsa según el SGBD que se use.
    private val jdbcURL: String

    // Si termina sin excepción, habrá creado la conexión.
    init
    {
        this.jdbcURL = connectionType.baseUrl+dbName
        try
        {// Aqui construimos la conexión
            connection = DriverManager.getConnection(jdbcURL, userName, password)
        }
        catch (e: SQLException)
        {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        catch (e: ClassNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}