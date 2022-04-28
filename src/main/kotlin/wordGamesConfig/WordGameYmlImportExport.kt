package wordGamesConfig

import com.charleskorn.kaml.Yaml
import java.io.File

class WordGameYmlImportExport(override val wordGameConfig: WordGameConfig,
                              fileName: String="wordleConfig.yml")
                              : WordGameImporExport(wordGameConfig, fileName)
{

    override fun serialize(): String = Yaml.default.encodeToString(WordGameConfig.serializer(),wordGameConfig)

    override fun serializeToDisk(fileName: String)
    {
        wordGameFile.also {it.writeText(this.serialize())}
    }

    override fun deserialize(serializedTxt: String): WordGameConfig
    {
        return Yaml.default.decodeFromString(WordGameConfig.serializer(), serializedTxt)
    }

    override fun deserializeFromDisk(): WordGameConfig = this.deserialize(wordGameFile.readText())

}