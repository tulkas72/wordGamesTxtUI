package wordGamesConfig

import java.io.File

abstract class WordGameImporExport(open val wordGameConfig: WordGameConfig, open val fileName:String)
{
    var wordGameFile= File(fileName)
    abstract fun serialize(): String
    abstract fun serializeToDisk(fileName: String)
    abstract fun deserialize(serializedTxt: String): WordGameConfig
    abstract fun deserializeFromDisk(): WordGameConfig
}
