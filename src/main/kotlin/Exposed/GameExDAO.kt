package Exposed

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object GameTable : IntIdTable()
{
    val name = varchar("name", 60)
    val rules = text("rules")
    val email = varchar("email", 255)

}

/**
 * Game ex d a o
 *
 * @constructor
 *
 * @param id
 */
class GameExDAO(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<PlayerExDAO>(PlayerTable)
    var name by  GameTable.name
    var rules by GameTable.rules
    var email by GameTable.email
    override fun toString(): String {
        return "GameExDAO(name='$name', rules='$rules', email='$email')"
    }
}

/**
 * C r u d_game ex dao
 *
 * @constructor Create empty C r u d_game ex dao
 */
class CRUD_GameExDao()
{
    /**
     * Insert game
     *
     * @param name
     * @param rules
     * @param email
     */
    fun insertGame(name:String, rules:String, email:String)
    {
        val game=GameExDAO.new{

        }

    }
}