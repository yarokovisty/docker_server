package com.example.features

import com.example.database.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class UserReceiveRemote(
    val username: String,
    val password: String
)

@Serializable
data class UserResponseRemote(
    val username: String,
    val password: String
)

fun userDTOToUserResponseRemote(userDTO: UserDTO): UserResponseRemote =
    UserResponseRemote(
        username = userDTO.username,
        password = userDTO.password
    )

fun userReceiveRemoteToUserDTO(userReceiveRemote: UserReceiveRemote): UserDTO =
    UserDTO(
        username = userReceiveRemote.username,
        password = userReceiveRemote.password
    )
