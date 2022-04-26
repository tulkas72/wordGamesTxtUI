package WordGamesConfig

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

abstract class WordGameImporExport(open val wordGameConfig: WordGameConfig, open val fileName:String)
{
    abstract fun serialize(): String
    abstract fun serializeToDisk(fileName: String)
    abstract fun deserialize(jsonTxt: String): WordGameConfig
    abstract fun deserializeFromDisk(): WordGameConfig
}


class WordGameJsonImportExport(override val wordGameConfig: WordGameConfig,
                               fileName: String="wordleConfig.json")
                               : WordGameImporExport(wordGameConfig, fileName)
{
    val wordleFile=File(fileName)

    override fun serialize(): String
    {
        return Json.encodeToString(wordGameConfig)
    }

    override fun serializeToDisk(fileName: String)
    {
        val json=this.serialize()
        var wordleFile = File(fileName).also {it.writeText(json)}
    }

    override fun deserialize(jsonTxt: String): WordGameConfig
    {
        return Json.decodeFromString<WordGameConfig>(jsonTxt)
    }

    override fun deserializeFromDisk(): WordGameConfig
    {
        val json2=wordleFile.readText()
        val wordleConfig= Json.decodeFromString<WordGameConfig>(json2)
        return wordleConfig
    }

}