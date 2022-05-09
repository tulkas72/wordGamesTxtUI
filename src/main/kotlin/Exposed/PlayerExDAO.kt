package Exposed

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object PlayerTable : IntIdTable()
{
    val name = varchar("name", 60)
    val nickname = varchar("nickname", 15)
    val email = varchar("email", 255)

}

class PlayerExDAO(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<PlayerExDAO>(PlayerTable)
    var name by  PlayerTable.name
    var nickanme by PlayerTable.nickname
    var email by PlayerTable.email

    override fun toString(): String
    {
        return "PlayerExDAO(name='$name', nickanme='$nickanme', email='$email')"
    }


}