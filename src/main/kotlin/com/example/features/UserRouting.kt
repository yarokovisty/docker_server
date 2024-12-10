package com.example.features

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUserRouting() {

    routing {
        get("/users") {
            val userController = UserController(call)
            userController.allUsers()
        }

        get("/user/{username}") {
            val userController = UserController(call)
            userController.user()
        }

        post("/user") {
            val userController = UserController(call)
            userController.new()
        }

        delete("/user/{username}") {
            val userController = UserController(call)
            userController.delete()
        }
    }
}