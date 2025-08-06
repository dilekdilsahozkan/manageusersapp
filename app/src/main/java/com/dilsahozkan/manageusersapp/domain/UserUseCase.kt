package com.dilsahozkan.manageusersapp.domain

import com.dilsahozkan.manageusersapp.common.BaseResult
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDetailDto
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDto
import com.dilsahozkan.manageusersapp.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun getUserInfo(): Flow<BaseResult<List<UserDto>>> = flow {
        val response = userRepository.getUserInfo()
        if (response.isSuccessful && response.code() == 200) {
            emit(BaseResult.Success(response.body() ?: emptyList(), response.code(), response.body().toString()))
        } else {
            emit(BaseResult.Error(response.body() ?: emptyList()))
        }
    }.catch { e ->
        emit(BaseResult.Error(listOf(UserDto(errorMessage = e.localizedMessage))))
    }

    fun getUserDetail(userId: String): Flow<BaseResult<UserDetailDto>> = flow {
        val response = userRepository.getUserDetail(userId)
        if (response.isSuccessful && response.code() == 200) {
            emit(BaseResult.Success(response.body() ?: UserDetailDto(), response.code(), response.body().toString()))
        } else {
            emit(BaseResult.Error(response.body() ?: UserDetailDto()))
        }
    }.catch { e ->
        emit(BaseResult.Error(UserDetailDto(errorMessage = e.localizedMessage)))
    }

    fun createUser(userDto: UserDetailDto): Flow<BaseResult<UserDetailDto>> = flow {
        val response = userRepository.createUser(userDto)
        if (response.isSuccessful && response.code() == 201) {
            emit(BaseResult.Success(response.body() ?: UserDetailDto(), response.code(), response.body().toString()))
        } else {
            emit(BaseResult.Error(response.body() ?: UserDetailDto()))
        }
    }.catch { e ->
        emit(BaseResult.Error(UserDetailDto(errorMessage = e.localizedMessage)))
    }

    fun updateUser(id: Int?, userDto: UserDetailDto): Flow<BaseResult<UserDetailDto>> = flow {
        val response = userRepository.updateUser(id, userDto)
        if (response.isSuccessful && response.code() == 200) {
            emit(BaseResult.Success(response.body() ?: UserDetailDto(), response.code(), response.body().toString()))
        } else {
            emit(BaseResult.Error(response.body() ?: UserDetailDto()))
        }
    }.catch { e ->
        emit(BaseResult.Error(UserDetailDto(errorMessage = e.localizedMessage)))
    }

    fun deleteUser(id: Int?): Flow<BaseResult<UserDetailDto>> = flow {
        val response = userRepository.deleteUser(id)
        if (response.isSuccessful && response.code() == 200) {
            val responseBodyJson = Gson().toJson(response.body() ?: UserDetailDto())
            emit(BaseResult.Success(response.body() ?: UserDetailDto(), response.code(), response.body().toString()))
        } else {
            emit(BaseResult.Error(response.body() ?: UserDetailDto()))
        }
    }.catch { e ->
        emit(BaseResult.Error(UserDetailDto(errorMessage = e.localizedMessage)))
    }
}