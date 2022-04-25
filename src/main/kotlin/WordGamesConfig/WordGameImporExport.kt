package WordGamesConfig

abstract class WordGameImporExport(open val wordGameConfig: WordGameConfig)
{
    abstract fun serialize(): String
    abstract fun serializeToDisk(): String
    abstract fun deserialize(): WordGameConfig
    abstract fun deserializeFromDisk(): WordGameConfig
}


class WordGameJsonImportExport(override val wordGameConfig: WordGameConfig):WordGameImporExport(wordGameConfig)
{
    override fun serialize(): String
    {
        TODO("Not yet implemented")
    }

    override fun serializeToDisk(): String
    {
        TODO("Not yet implemented")
    }

    override fun deserialize(): WordGameConfig
    {
        TODO("Not yet implemented")
    }

    override fun deserializeFromDisk(): WordGameConfig
    {
        TODO("Not yet implemented")
    }

}