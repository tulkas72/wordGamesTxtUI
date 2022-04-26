package wordGamesConfig
import kotlinx.serialization.*


@Serializable
data class WordGameConfig(val gameName: String, val wordLength: Int,
                          val maxTrials: Int)


