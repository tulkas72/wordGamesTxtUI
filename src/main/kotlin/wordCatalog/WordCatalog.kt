package wordCatalog

interface WordCatalog
{
    var wordLength: Int?

    suspend fun load(fileName: String)
    fun addWord(word: String)
    fun getWordAt(index: Int): String
    fun getWordsStartingWith(letter_s: String): List<String>
    fun getRandomWord(): String
    fun getRandomWord(length: Int): String
    fun nLetterWords(length:Int): List<String>
    fun find(word: String): Int
    fun size(): Int
    fun isEmpty(): Boolean
}