package dataBases

open class DBConnect
{

    protected var dbName: String = ""
    protected var dbUser: String = ""
    protected var dbPass: String = ""
    protected var dbHost: String = ""
    protected var dbPort: String = ""
    protected var dbType: DBConnectType = DBConnectType.NOT_SET;

    constructor(dbName: String, dbType:DBConnectType,
                dbUser: String, dbPass: String, dbHost: String="")
    {
        this.dbName = dbName
        this.dbUser = dbUser
        this.dbPass = dbPass
        this.dbHost = dbHost
        this.dbType = dbType
    }

}



enum class DBConnectType(val baseUrl: String)
{
    MYSQL("jdbc:mysql://localhost:3306/"),
    SQLITE("jdbc:sqlite:"), // File name, incuding path, must be added later in code
    POSTGRESQL("jdbc:postgresql://localhost:5432/"),
    ORACLE("jdbc:oracle:thin:@localhost:1521:"), //Tested up to here
    NOT_SET(""),

    MONGODB("mongodb://localhost:27017/"), // Untested
    REDIS("redis://localhost:6379/"), // Untested
    MEMCACHED("memcached://localhost:11211/"), // Untested
    CASSANDRA("cassandra://localhost:9042/"), // Untested
    ELASTICSEARCH("elasticsearch://localhost:9200/"),
    MAPDB("mapdb://localhost:9000/") // Untested
}