package wordGames

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class WordleTest {
    private var wordle = Wordle()
    private var guess = wordle.checkWord("orden")


    @Test
    fun getWordCatalog() {
    }

    @Test
    fun setWordCatalog() {
    }

    @Test
    fun getGameName() {
    }

    @Test
    fun getGameDescription() {
    }

    @Test
    fun setGameDescription() {
    }

    @Test
    fun getGameInstructions() {
    }

    @Test
    fun setGameInstructions() {
    }

    @Test
    fun getWordLength() {
    }

    @Test
    fun setWordLength() {
    }

    @Test
    fun getWord() {
    }

    @Test
    fun setWord() {
    }

    @Test
    fun getLettersPositions() {
    }

    @Test
    fun setLettersPositions() {
    }

    @Test
    fun getMaxTrials() {
    }

    @Test
    fun setMaxTrials() {
    }

    @Test
    fun setInstructions() {
    }

    @Test
    fun setDescription() {
    }

    @Test
    fun checkWord() {
        val myGuess:wordleGuess = mutableMapOf(
            0 to Pair('o',letterSquareState.NOT_IN_WORD),
            1 to Pair('r',letterSquareState.NOT_IN_WORD),
            2 to Pair('d',letterSquareState.NOT_IN_WORD),
            3 to Pair('e',letterSquareState.NOT_IN_WORD),
            4 to Pair('n',letterSquareState.NOT_IN_WORD))
        println(myGuess)
        println(guess)
        assertEquals(guess,myGuess)
    }

    @Test
    fun correctGuess() {
    }
}