import java.io.File
import java.io.FileNotFoundException


class WordListReader
{
    private val letters: List<String> =
        listOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z")

    fun readList(fileName: String): MutableList<String> {
        val filename = this::class.java.getResource("/$fileName")?.path
            ?: throw FileNotFoundException("File $fileName not found")

        val file = File(filename)


        return file.readLines().map { it.trim() }.toMutableList()
    }

    fun readMap(fileName: String, beginWith:List<String> = letters): MutableMap<String,List<String> >
    {
        val list: MutableList<String> = readList(fileName)
        val dict: MutableMap<String,List<String> > = mutableMapOf()


        for (letter in letters)
        {
            dict[letter] = list.filter { it.lowercase().startsWith(letter.toString()) }
        }
        return dict
    }
}