package wordGames
import wordCatalog.ListWordCatalog
import wordCatalog.WordCatalog

import kotlinx.coroutines.launch

import kotlinx.coroutines.*

typealias wordleGuess = MutableMap<Int,Pair<Char,letterSquareState> >


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
    protected var listOfMoves= mutableListOf<wordleGuess>()

    constructor(gameName: String,
                maxTrials:Int=6,
                wordLength: Int=5,
                dictionary: String = "Spanish.dic",
                wordCatalog: WordCatalog=ListWordCatalog())
    {
        this.wordCatalog = wordCatalog
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
