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

    override fun serialize(): String = Json.encodeToString(wordGameConfig)

    override fun serializeToDisk(fileName: String) //quitar parámetro
    {
          wordleFile.also {it.writeText(this.serialize())}
    }

    override fun deserialize(serializedTxt: String): WordGameConfig = Json.decodeFromString(serializedTxt)
    override fun deserializeFromDisk(): WordGameConfig = this.deserialize(wordleFile.readText())
}