import wordGamesConfig.WordGameConfig
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import wordCatalog.ListWordCatalog
import wordGames.Wordle
import wordGames.letterSquareState
import java.io.File
import com.github.doyaaaaaken.kotlincsv.dsl.context.WriteQuoteMode
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import dataBases.DBAccess
import dataBases.DBConnectType
import dataBases.Player
import wordGamesConfig.WordGamesCsvImportExport
import java.sql.DriverManager

/**
 * Main
 *
 * @param args
 */
suspend fun main(args: Array<String>)
{
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
    val esDic=ListWordCatalog()
    esDic.load("Spanish.dic")
    println(esDic.find ("hola"))
    println(esDic.getWordsStartingWith("ho"))
    println(esDic.size)
    println(esDic.nLetterWords(5))

    var wordle:Wordle=Wordle()
    val checkWord = wordle.checkWord("holas")

    checkWord.forEach {
        when(it.value.second)
            {
                letterSquareState.IN_SPOT->print("${it.value.first}*")
                letterSquareState.WRONG_SPOT->print("${it.value.first}-")
                letterSquareState.NOT_IN_WORD->print("${it.value.first}X")
            }
        }
    println("\n $checkWord")
    val json=wordle.jsonSerialize()
    val yaml=wordle.yamlSerialize()

    var wordleFile = File("wordleConfig.json").also {
        it.writeText(json)
    }

    val json2=wordleFile.readText()
    val wordleConfig= Json.decodeFromString<WordGameConfig>(json2)

    File("wordleConfig.yml").also {
        it.writeText(yaml)
    }

    print(wordleConfig)
    println("CSV")
    print(WordGamesCsvImportExport(wordleConfig).serialize())

    val writer = csvWriter {
        charset = "UTF-8"
        delimiter = '\t'
        nullCode = "NULL"
        lineTerminator = "\n"
        outputLastLineTerminator = true
        quote {
            mode = WriteQuoteMode.ALL
            char = '\''
        }
    }
    var connect=DBAccess("wordGames.sqlite",DBConnectType.SQLITE)
    var players:List<Player> = connect.listPlayers()

    players.forEach {
        println(it)
    }

    val games=connect.listGames()
    games.forEach {
        println(it)
    }

    val matches=connect.listMatches()
    matches.forEach {
        println(it)
    }

    connect.close()
}