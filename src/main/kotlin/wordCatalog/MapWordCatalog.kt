package wordCatalog

class MapWordCatalog: WordCatalog
{
    override var wordLength: Int?
        get() = TODO("Not yet implemented")
        set(value) {}

    override suspend fun load(fileName: String) {
        TODO("Not yet implemented")
    }

    override fun addWord(word: String) {
        TODO("Not yet implemented")
    }

    override fun getWordAt(index: Int): String {
        TODO("Not yet implemented")
    }

    override fun getWordsStartingWith(letter_s: String): List<String> {
        TODO("Not yet implemented")
    }

    override fun getRandomWord(): String {
        TODO("Not yet implemented")
    }

    override fun getRandomWord(length: Int): String {
        TODO("Not yet implemented")
    }

    override fun nLetterWords(length: Int): List<String> {
        TODO("Not yet implemented")
    }

    override fun find(word: String): Int {
        TODO("Not yet implemented")
    }

    override fun size(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }
}