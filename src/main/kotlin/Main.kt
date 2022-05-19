import com.github.doyaaaaaken.kotlincsv.dsl.context.WriteQuoteMode
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import dataBases.DBAccess
import dataBases.DBConnectType
import dataBases.Player
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.CP437TilesetResources.rexPaint16x16
import org.hexworks.zircon.api.DrawSurfaces.tileGraphicsBuilder
import org.hexworks.zircon.api.Shapes.buildRectangle
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import wordCatalog.ListWordCatalog
import wordGames.Wordle
import wordGames.letterSquareState
import wordGamesConfig.WordGameConfig
import wordGamesConfig.WordGamesCsvImportExport
import java.io.File

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

    val config = AppConfig.newBuilder()
        .withDefaultTileset(rexPaint16x16())
        .build()

    val tileGrid = startTileGrid(config)

    val background = tileGraphicsBuilder()
        .withSize(tileGrid.size) // you can fetch the size of a TileGrid like this
        .withFiller(Tile.newBuilder()
            .withCharacter(Symbols.BULLET)
            .withBackgroundColor(ANSITileColor.BLUE)
            .withForegroundColor(ANSITileColor.CYAN)
            .build())
        .build()

    val rectangle = buildRectangle(
        Position.zero(),
        tileGrid.size)
        .toTileGraphics(Tile.newBuilder()
            .withCharacter(Symbols.BLOCK_DENSE)
            .withBackgroundColor(TileColor.transparent())
            .withForegroundColor(ANSITileColor.RED)
            .build(),
            config.defaultTileset)

    background.draw(rectangle, Position.zero())

// the default position is (0x0) which is the top left corner

// the default position is (0x0) which is the top left corner
    tileGrid.draw(background, Position.zero())


   /* screen.draw(graphics, Position.zero(),graphics.size)

    screen.display()
    screen.theme = ColorThemes.arc()*/

    //Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

}