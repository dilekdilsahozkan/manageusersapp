package com.dilsahozkan.manageusersapp.data.remote.api

import com.dilsahozkan.manageusersapp.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("users")
    suspend fun getUserInfo(): Response<List<UserDto>>

    @POST("users")
    suspend fun createUser(
        @Body createNewUser: UserDto
    ): Response<UserDto>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: UserDto): Response<UserDto>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<UserDto>
}