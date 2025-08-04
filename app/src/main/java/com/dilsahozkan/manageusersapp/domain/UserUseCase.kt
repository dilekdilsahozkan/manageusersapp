package com.dilsahozkan.manageusersapp.domain

import com.dilsahozkan.manageusersapp.common.BaseResult
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDto
import com.dilsahozkan.manageusersapp.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun getUserInfo(): Flow<BaseResult<List<UserDto>>> = flow {
        val value = userRepository.getUserInfo()
        if (value.isSuccessful && value.code() == 200) {
            emit(BaseResult.Success(value.body() ?: emptyList()))
        } else {
            emit(BaseResult.Error(value.body() ?: emptyList()))
        }
    }.catch { e ->
        emit(BaseResult.Error(listOf(UserDto(errorMessage = e.localizedMessage))))
    }

    fun createUser(userDto: UserDto): Flow<BaseResult<UserDto>> = flow {
        val response = userRepository.createUser(userDto)
        if (response.isSuccessful && response.code() == 201) {
            emit(BaseResult.Success(response.body() ?: UserDto()))
        } else {
            emit(BaseResult.Error(response.body() ?: UserDto()))
        }
    }.catch { e ->
        emit(BaseResult.Error(UserDto(errorMessage = e.localizedMessage)))
    }

    fun updateUser(id: Int, userDto: UserDto): Flow<BaseResult<UserDto>> = flow {
        val response = userRepository.updateUser(id, userDto)
        if (response.isSuccessful && response.code() == 200) {
            emit(BaseResult.Success(response.body() ?: UserDto()))
        } else {
            emit(BaseResult.Error(response.body() ?: UserDto()))
        }
    }.catch { e ->
        emit(BaseResult.Error(UserDto(errorMessage = e.localizedMessage)))
    }

    fun deleteUser(id: Int): Flow<BaseResult<UserDto>> = flow {
        val response = userRepository.deleteUser(id)
        if (response.isSuccessful && response.code() == 200) {
            emit(BaseResult.Success(response.body() ?: UserDto()))
        } else {
            emit(BaseResult.Error(response.body() ?: UserDto()))
        }
    }.catch { e ->
        emit(BaseResult.Error(UserDto(errorMessage = e.localizedMessage)))
    }
}