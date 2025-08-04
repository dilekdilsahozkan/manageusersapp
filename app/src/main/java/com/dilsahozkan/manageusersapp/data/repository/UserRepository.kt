package com.dilsahozkan.manageusersapp.data.repository

import com.dilsahozkan.manageusersapp.data.remote.api.UserService
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDto
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: UserService){
    suspend fun getUserInfo() : Response<List<UserDto>> = service.getUserInfo()
    suspend fun createUser( createNewUser: UserDto) : Response<UserDto> = service.createUser(createNewUser)
    suspend fun updateUser(id : Int, userDto: UserDto) : Response<UserDto> = service.updateUser(id, userDto)
    suspend fun deleteUser(id : Int) : Response<UserDto> = service.deleteUser(id)
}