package wordGamesConfig

import com.floern.castingcsv.castingCSV
import java.io.File

class WordGamesCsvImportExport(override val wordGameConfig: WordGameConfig,
                               fileName: String = "wordleConfig.csv",
                              ) : WordGameImporExport(wordGameConfig, fileName)

{

    override fun serialize(): String
    {
        val tmp= wordGameConfig.toString().replace("WordGameConfig(", "").replace(")", "")
        val lst= tmp.split(",")
        var str1:String=""
        var str2:String=""
        for(ele in lst)
        {
            str1+=ele.split("=")[0]+","
            str2+=ele.split("=")[1]+","
        }
        str1=str1.substring(0, str1.length-1)+"\n"
        str2=str2.substring(0,str2.length-1)+"\n"

        return str1+str2
    }

    override fun serializeToDisk(fileName: String)
    {
        val str=this.serialize()
        val lst= str.split("\n")
        castingCSV().toCSV(lst, wordGameFile.outputStream())

    }

    override fun deserialize(serializedTxt: String): WordGameConfig {
        TODO("Not yet implemented")
        val tmp2 = castingCSV().fromCSV<WordGameConfig>(wordGameFile)
    }

    override fun deserializeFromDisk(): WordGameConfig {
        TODO("Not yet implemented")
    }
}