package wordGamesConfig

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class WordGameJsonImportExport(override val wordGameConfig: WordGameConfig,
                               fileName: String="wordleConfig.json")
                               : WordGameImporExport(wordGameConfig, fileName)
{
    val wordleFile= File(fileName)

    override fun serialize(): String
    {
        return Json.encodeToString(wordGameConfig)
    }

    override fun serializeToDisk(fileName: String) //quitar parámetro
    {
        val json=this.serialize()
        wordleFile.also {it.writeText(json)}
    }

    override fun deserialize(jsonTxt: String): WordGameConfig = Json.decodeFromString(jsonTxt)

    override fun deserializeFromDisk(): WordGameConfig = this.deserialize(wordleFile.readText())
}