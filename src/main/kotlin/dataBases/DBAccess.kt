package dataBases
import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.time.LocalDate

open class DBAccess
{

    protected var dbName: String = ""
    protected var dbUser: String = ""
    protected var dbPass: String = ""
    protected var dbHost: String = ""
    protected var dbPort: String = ""
    protected var dbType: DBConnectType = DBConnectType.NOT_SET;
    protected var url: String = ""
    protected val connection: Connection?

    constructor(dbName: String, dbType:DBConnectType,
                dbUser: String="", dbPass: String="", dbHost: String="")
    {
        this.dbName = dbName //file name for sqlite
        this.dbUser = dbUser
        this.dbPass = dbPass
        this.dbHost = dbHost
        this.dbType = dbType
        when(dbType)
        {
            DBConnectType.SQLITE-> {
                this.url = dbType.baseUrl + dbName
                connection = DriverManager.getConnection(url)
            }
            else->{
                this.url = dbType.baseUrl + "/" + dbName
                connection = DriverManager.getConnection(url, dbUser, dbPass)
            }
        }
        //require(connection != null) { "Error connecting to database" }
        //^^ that line over here allows us to get rid of the ifs testing for null in listing functions
        // Oracle Connection conn = DriverManager.getConnection
        //  ("jdbc:oracle:thin:@myhost:1521:orcl", "scott", "tiger");
    }

    fun listPlayers(): List<Player>
    {
        val players = mutableListOf<Player>()
        // true if the connection is valid
        if(connection?.isValid(0) == true)
        {
            // the query is only prepared not executed
            val query = connection.prepareStatement("SELECT * FROM Player")

            // the query is executed and results are fetched
            val result = query.executeQuery()
            while(result.next())
            {

                // getting the value of the id column
                val id = result.getInt("id")

                // getting the value of the name column
                val name = result.getString("name")
                // getting the value of the nickname column
                val nickname = result.getString("nickname")
                // getting the value of the email column
                val email = result.getString("email")

                /*
                constructing a Player object and
                putting data into the list
                 */
                players.add(Player(id, name, nickname, email))
            }
        }
        return players
    }

    fun listGames(): List<Game>
    {
        val games = mutableListOf<Game>()
        // true if the connection is valid
        if(connection?.isValid(0) == true)
        {
            // the query is only prepared not executed
            val query = connection.prepareStatement("SELECT * FROM Game")
            val result = query.executeQuery()
            while(result.next())
            {
                val id=result.getInt("id")
                val name=result.getString("name")
                val rules=result.getString("rules")
                val lastWord=result.getString("lastWord")

                games.add(Game(id, name, rules, lastWord))
            }
        }

        return games
    }

    fun listMatches(): List<MatchesWithNames>
    {
        val matches = mutableListOf<MatchesWithNames>()
        //Another option for this is to use a join query,
        // or query only Matches and then query for the name
        // of players and games in other two queries
        val query: PreparedStatement?
        query =
            connection?.prepareStatement("SELECT Matches.id, Player.name as pname, Game.name as gname," +
                                         " win_loss, word, score, game_date " +
                                         " FROM Matches, Player, Game" +
                                         " WHERE Matches.playerId = Player.id AND Matches.gameId = Game.id")

        val result = query?.executeQuery()

        while (result?.next() == true)
        {
            val id = result.getInt("id")
            val playerName = result.getString("pname")
            val gameName = result.getString("gname")
            val win_loss = result.getInt("win_loss")
            val word = result.getString("word")
            val score = result.getInt("score")

            val gameDate:LocalDate =
                when(dbType)
                {
                   DBConnectType.SQLITE -> LocalDate.parse(result.getString("game_date").toString())

                   else -> result.getDate("game_date").toLocalDate()
                }

            matches.add(MatchesWithNames(id, gameName, playerName, win_loss, word, score, gameDate))
        }

        return matches
    }

    fun close()
    {
        connection?.close()
    }

}



