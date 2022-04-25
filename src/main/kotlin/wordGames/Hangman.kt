package wordGames

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