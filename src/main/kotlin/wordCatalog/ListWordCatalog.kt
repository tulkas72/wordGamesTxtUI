package wordCatalog

import WordListReader


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

class ListWordCatalog: WordCatalog
{
    private val words = mutableListOf<String>()
    private val wordListReader = WordListReader()
    override var wordLength: Int? = null
    var size: Int = words.size
        get() = words.size
        private set

    // with length we pretend to have only words of that length in letters
    // TODO: implement this
    constructor(wordLength: Int=5)
    {
        this.wordLength = wordLength
    }

    override suspend fun load(fileName: String)
    {
        words.addAll(wordListReader.readList(fileName))

    }

    override fun addWord(word: String)
    {
        words.add(word)
    }

    override fun getWordAt(index: Int): String
    {
        return words[index]
    }

    override fun getWordsStartingWith(letter_s: String): List<String>
    {
        return words.filter { it.lowercase().startsWith(letter_s.toString()) }
    }

    override fun getRandomWord(): String
    {
        return words.random()
    }

    override fun getRandomWord(length: Int): String
    {
        return words.filter { it.length == length }.random()
    }

    override fun nLetterWords(length:Int): List<String>
    {
        return words.filter { it.length == length }
    }

    override fun find(word: String): Int
    {
        return words.indexOf(word)
    }

    override fun size(): Int
    {
        return words.size
    }

    override fun isEmpty(): Boolean
    {
        return words.isEmpty()
    }

}