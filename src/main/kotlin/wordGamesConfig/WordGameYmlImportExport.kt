package wordGamesConfig

import com.charleskorn.kaml.Yaml
import java.io.File

class WordGameYmlImportExport(override val wordGameConfig: WordGameConfig,
                              fileName: String="wordleConfig.yml")
                              : WordGameImporExport(wordGameConfig, fileName)
{
    val wordleFile= File(fileName)

    override fun serialize(): String = Yaml.default.encodeToString(WordGameConfig.serializer(),wordGameConfig)

    override fun serializeToDisk(fileName: String)
    {
        wordleFile.also {it.writeText(this.serialize())}
    }

    override fun deserialize(serializedTxt: String): WordGameConfig
    {
        return Yaml.default.decodeFromString(WordGameConfig.serializer(), serializedTxt)
    }

    override fun deserializeFromDisk(): WordGameConfig = this.deserialize(wordleFile.readText())

}