package dataBases

enum class DBConnectType(val baseUrl: String)
{
    MYSQL("jdbc:mysql://localhost:3306"),
    SQLITE("jdbc:sqlite:"), // File name, incuding path, must be added later in code
    POSTGRESQL("jdbc:postgresql://localhost:5432"),
    ORACLE("jdbc:oracle:thin:@localhost:1521:"), //Tested up to here
    NOT_SET(""),
    H2("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"),

    MONGODB("mongodb://localhost:27017"), // Untested
    REDIS("redis://localhost:6379"), // Untested
    MEMCACHED("memcached://localhost:11211"), // Untested
    CASSANDRA("cassandra://localhost:9042"), // Untested
    ELASTICSEARCH("elasticsearch://localhost:9200"),
    MAPDB("mapdb://localhost:9000") // Untested
}