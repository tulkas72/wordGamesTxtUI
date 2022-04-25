package wordGames

enum class letterSquareState
{
    IN_SPOT, // The letter is correct and is in the correct spot
    WRONG_SPOT, // The letter is correct but is in the wrong spot
    NOT_IN_WORD,// The letter is not correct
}