package wordGames
import WordGamesConfig.WordGameConfig
import com.charleskorn.kaml.Yaml
import wordCatalog.ListWordCatalog
import wordCatalog.WordCatalog

import kotlinx.coroutines.launch

import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


abstract class WordGame
{
    var wordCatalog: WordCatalog
    protected val gameName:String
    protected open var gameDescription:String = ""
    protected open var gameInstructions:String = ""
    protected var wordLength:Int
    protected var word:String = ""
    protected open lateinit var lettersPositions: MutableMap<Char,MutableList<Int>>
    protected var maxTrials:Int


    constructor(gameName: String,
                maxTrials:Int=6,
                wordLength: Int=5,
                dictionary: String = "Spanish.dic")
    {
        this.wordCatalog = ListWordCatalog()
        runBlocking {  // launch a new coroutine in background and continue
            launch { wordCatalog.load(dictionary) }
        }

        this.gameName = gameName
        this.wordLength = wordLength
        this.maxTrials = maxTrials
        //Choose a random word from the word catalog
        word = wordCatalog.getRandomWord(wordLength)
        //For testing purposes
        word = "casas"
        initLetterPositions()
    }


    private fun initLetterPositions()
    {
        lettersPositions = mutableMapOf()
        for (i in 0 until wordLength)
        {
            if(!lettersPositions.containsKey(word[i]))
            {
                lettersPositions[word[i]] = mutableListOf(i)
            }
            else
            {
                lettersPositions[word[i]]!!.add(i)
            }
        }
    }
    // This two methods are unnecessary, but they are here to show it is possible if we want to change the game
    // instructions or description from outside the class hierarchy
    protected open fun setInstructions(instructions: String)
    {
        gameInstructions = " $instructions"
    }

    protected open fun setDescription(description: String)
    {
        gameDescription = " $description"
    }
}


typealias wordleGuess = MutableMap<Int,Pair<Char,letterSquareState> >

class WordleResult
{

}

class Wordle: WordGame
{
    private var guessResult: MutableMap<Int,Pair<Char,letterSquareState> > = mutableMapOf()


    constructor(maxTrials:Int=6,
                wordLength: Int=5,
                dictionary: String = "Spanish.dic")
               : super("Wordle",maxTrials,wordLength,dictionary)
    {
        this.wordLength = wordLength
        gameDescription = "Wordle is a word game where you try to form words from the letters you draw."
        gameInstructions = "Draw a word by clicking on the letters.  The letters will be removed from the board."
    }

    fun checkWord(guessWord: String): wordleGuess
    {
        for(i in 0 until wordLength)
        {
            if(word[i]==guessWord[i])
               {  guessResult[i] = Pair(word[i],letterSquareState.IN_SPOT)  }
            else
               {
                    if(lettersPositions.containsKey(guessWord[i]))
                       { guessResult[i] = Pair(guessWord[i],letterSquareState.WRONG_SPOT) }
                    else
                       {  guessResult[i] = Pair(guessWord[i],letterSquareState.NOT_IN_WORD) }
               }
        }
        return guessResult
    }

    fun correctGuess(guessWord: String): Boolean
    {
        checkWord(guessWord)
        guessResult.forEach {
            if(it.value.second != letterSquareState.IN_SPOT)
            {
                return false
            }
        }
        return true
    }

    fun jsonSerialize(): String
    {
        val json = Json.encodeToString(WordGameConfig(gameName,wordLength,maxTrials))
        return json
    }

    fun yamlSerialize():String
    {
        val yamlTextval  = Yaml.default.encodeToString(WordGameConfig.serializer(), WordGameConfig(gameName,wordLength,maxTrials))
        return yamlTextval
    }

}


enum class letterSquareState
{
    IN_SPOT, // The letter is correct and is in the correct spot
    WRONG_SPOT, // The letter is correct but is in the wrong spot
    NOT_IN_WORD,// The letter is not correct
}


class Hangman: WordGame
{
    constructor(maxTrials: Int,
                wordLength: Int = 5,
                dictionary: String = "Spanish.dic")
              :super("Hangman", maxTrials, wordLength,dictionary)
    {
        this.wordLength = wordLength
        gameDescription = "Hangman is a word game where you try to guess the word by drawing the letters."
        gameInstructions = "Draw a word by clicking on the letters.  The letters will be removed from the board."
    }
}