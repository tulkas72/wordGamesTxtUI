package wordGamesConfig

import com.floern.castingcsv.castingCSV
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File

class WordGamesCsvImportExport(override val wordGameConfig: WordGameConfig,
                               fileName: String = "wordleConfig.csv",
                              ) : WordGameImporExport(wordGameConfig, fileName)

{
    val wordleFile= File(fileName)

    override fun serialize(): String
    {
        val tmp= wordGameConfig.toString().replace("WordGameConfig(", "").replace(")", "")
        val lista= tmp.split(",")
        var str1:String=""
        var str2:String=""
        for(ele in lista)
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
        TODO("Not yet implemented")
    }

    override fun deserialize(serializedTxt: String): WordGameConfig {
        TODO("Not yet implemented")
        val tmp2 = castingCSV().fromCSV<WordGameConfig>(wordleFile)
    }

    override fun deserializeFromDisk(): WordGameConfig {
        TODO("Not yet implemented")
    }
}