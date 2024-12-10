package com.example.features

import com.example.database.UserModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class UserController(private val call: ApplicationCall) {

    suspend fun allUsers() {
        try {
            val users = UserModel.fetchAllUsers().map(::userDTOToUserResponseRemote)
            call.respond(HttpStatusCode.OK, users)
        } catch (ex: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Не удалось получить пользователей")
        }
    }

    suspend fun user() {
        try {
            val username = call.parameters["username"]

            if (username == null) {
                call.respond(HttpStatusCode.BadRequest, "username не может быть null")
            } else {
                val userDTO = UserModel.fetchUser(username)
                val user = userDTOToUserResponseRemote(userDTO)
                call.respond(HttpStatusCode.Found, user)
            }
        } catch (ex: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Не удалось получить пользователя")
        }
    }

    suspend fun new() {
        try {
            val receive = call.receive(UserReceiveRemote::class)
            val userDTO = userReceiveRemoteToUserDTO(receive)
            UserModel.insert(userDTO)
            call.respond(HttpStatusCode.Created)
        } catch (ex: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Не удалось создать пользователя")
        }
    }

    suspend fun delete() {
        try {
            val username = call.parameters["username"]

            if (username == null) {
                call.respond(HttpStatusCode.BadRequest, "username не может быть null")
            } else {
                val success = UserModel.deleteUser(username)

                if (success) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond("Нет пользователя с username = ${username}")
                }
            }
        } catch (ex: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Не удалось удалить пользователя")
        }
    }
}