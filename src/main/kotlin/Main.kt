import WordGamesConfig.WordGameConfig
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import wordCatalog.ListWordCatalog
import wordGames.Wordle
import wordGames.letterSquareState
import java.io.File


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
            { letterSquareState.IN_SPOT->print("${it.value.first}*")
                letterSquareState.WRONG_SPOT->print("${it.value.first}-")
                letterSquareState.NOT_IN_WORD->print("${it.value.first}X")
            }
        }
    println("\n $checkWord")
    val json=wordle.serialize()

    val wordleFile = File("wordleConfig.json").also {
        it.writeText(json)
    }
    val json2=wordleFile.readText()
    val wordleConfig= Json.decodeFromString<WordGameConfig>(json2)
    print(wordleConfig)
}