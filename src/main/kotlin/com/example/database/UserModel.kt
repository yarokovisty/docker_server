package com.example.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object UserModel : Table("users") {
    private val id = UserModel.integer("id")
    private val username = UserModel.varchar("username", 30)
    private val password = UserModel.varchar("password", 30)

    fun insert(userDTO: UserDTO) {
        transaction {
            UserModel.insert {
                it[username] = userDTO.username
                it[password] = userDTO.password
            }
        }
    }

    fun fetchAllUsers(): List<UserDTO> = transaction {
        return@transaction UserModel.selectAll().map(::userRowToUserDTO)
    }

    fun fetchUser(username: String): UserDTO = transaction {
        UserModel.selectAll().where { UserModel.username eq username }.map(::userRowToUserDTO).single()
    }

    fun deleteUser(username: String): Boolean = transaction {
        val countUser = UserModel.deleteWhere { UserModel.username eq username }
        return@transaction countUser == 1
    }

    private fun userRowToUserDTO(userRow: ResultRow): UserDTO =
        UserDTO(
            username = userRow[username],
            password = userRow[password]
        )
}