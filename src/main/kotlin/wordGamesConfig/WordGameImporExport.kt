package wordGamesConfig

abstract class WordGameImporExport(open val wordGameConfig: WordGameConfig, open val fileName:String)
{
    abstract fun serialize(): String
    abstract fun serializeToDisk(fileName: String)
    abstract fun deserialize(jsonTxt: String): WordGameConfig
    abstract fun deserializeFromDisk(): WordGameConfig
}
