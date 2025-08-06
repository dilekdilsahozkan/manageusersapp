package com.dilsahozkan.manageusersapp.data.repository

import com.dilsahozkan.manageusersapp.data.remote.api.UserService
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDetailDto
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDto
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: UserService){
    suspend fun getUserInfo() : Response<List<UserDto>> = service.getUserInfo()
    suspend fun getUserDetail(userId: String): Response<UserDetailDto> = service.getUserDetail(userId)
    suspend fun createUser(createNewUser: UserDetailDto) : Response<UserDetailDto> = service.createUser(createNewUser)
    suspend fun updateUser(id : Int?, userDto: UserDetailDto) : Response<UserDetailDto> = service.updateUser(id, userDto)
    suspend fun deleteUser(id : Int?) : Response<UserDetailDto> = service.deleteUser(id)
}