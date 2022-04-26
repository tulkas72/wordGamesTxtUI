package WordGamesConfig

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

abstract class WordGameImporExport(open val wordGameConfig: WordGameConfig)
{
    abstract fun serialize(): String
    abstract fun serializeToDisk(fileName: String): String
    abstract fun deserialize(): WordGameConfig
    abstract fun deserializeFromDisk(): WordGameConfig
}


class WordGameJsonImportExport(override val wordGameConfig: WordGameConfig) : WordGameImporExport(wordGameConfig)
{

    override fun serialize(): String
    {
        return Json.encodeToString(wordGameConfig)
    }

    override fun serializeToDisk(fileName: String): String
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