package dataBases

import java.sql.SQLException

class SQLUtils
{
    companion object {
        fun printSQLException(ex: SQLException)
        {   for (e in ex)
            {   if (e is SQLException)
                {   e.printStackTrace(System.err)
                    System.err.println("SQLState: " + e.sqlState)
                    System.err.println("Error Code: " + e.errorCode)
                    System.err.println("Message: " + e.message)
                    var t = ex.cause
                    while (t != null)
                    {   println("Cause: $t")
                        t = t.cause
                    }
                }
            }
        }
    }
}