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
import org.hexworks.zircon.api.*
import wordGamesConfig.WordGamesCsvImportExport

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.TileGraphics
import org.jetbrains.exposed.sql.Database

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
    //var connect=DBAccess("WordGames",DBConnectType.SQLITE)
    val connect=DBAccess("WordGames",DBConnectType.MYSQL,"wordGames","wordGames")
    val players:List<Player> = connect.listPlayers()

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

    val tileGrid = SwingApplications.startTileGrid(
        AppConfig.newBuilder()
            .withSize(60, 30)
            .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
            .build()
    )

    val screen = tileGrid.toScreen()

    screen.addComponent(
        Components.label()
            .withText("Hello, Zircon!")
            .withPosition(23, 10)
    )

     val graphics: TileGraphics = DrawSurfaces.tileGraphicsBuilder()
        .withSize(40, 40)
        .withTileset(CP437TilesetResources.rexPaint16x16())
        .withFiller(Tile.newBuilder()
            .withCharacter('x')
            .build())
        .build();

    screen.draw(graphics, Position.zero(),graphics.size)

    screen.display()
    screen.theme = ColorThemes.arc()

    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

}