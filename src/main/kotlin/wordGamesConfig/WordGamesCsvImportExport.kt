package wordGamesConfig

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File

class WordGamesCsvImportExport(override val wordGameConfig: WordGameConfig,
                               fileName: String = "wordleConfig.yml",
                              ) : WordGameImporExport(wordGameConfig, fileName)

{
    val wordleFile= File(fileName)

    override fun serialize(): String
    {
        TODO("Not yet implemented")
    }

    override fun serializeToDisk(fileName: String)
    {
        TODO("Not yet implemented")
    }

    override fun deserialize(serializedTxt: String): WordGameConfig {
        TODO("Not yet implemented")
    }

    override fun deserializeFromDisk(): WordGameConfig {
        TODO("Not yet implemented")
    }
}