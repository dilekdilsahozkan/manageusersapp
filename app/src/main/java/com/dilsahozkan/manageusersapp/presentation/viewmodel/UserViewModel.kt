package com.dilsahozkan.manageusersapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dilsahozkan.manageusersapp.common.BaseResult
import com.dilsahozkan.manageusersapp.common.BaseViewModel
import com.dilsahozkan.manageusersapp.common.ViewState
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDetailDto
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDto
import com.dilsahozkan.manageusersapp.domain.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userUseCase: UserUseCase) :
    BaseViewModel<UserDto>()  {

    var _userListState: MutableStateFlow<ViewState<List<UserDto>>> =
        MutableStateFlow(ViewState.Idle())
    val userListState: StateFlow<ViewState<List<UserDto>>> = _userListState

    var _userState: MutableStateFlow<ViewState<UserDetailDto>> =
        MutableStateFlow(ViewState.Idle())
    val userState: StateFlow<ViewState<UserDetailDto>> = _userState

    var _updateState: MutableStateFlow<ViewState<UserDetailDto>> =
        MutableStateFlow(ViewState.Idle())
    val updateState: StateFlow<ViewState<UserDetailDto>> = _updateState

    var _deleteState: MutableStateFlow<ViewState<UserDetailDto>> =
        MutableStateFlow(ViewState.Idle())
    val deleteState: StateFlow<ViewState<UserDetailDto>> = _deleteState

    fun getUserInfo() {
        viewModelScope.launch {
            userUseCase.getUserInfo()
                .onStart {
                    _userListState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _userListState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _userListState.value = ViewState.Success(result.data, result.responseCode, result.message)
                        }

                        is BaseResult.Error -> {
                            _userListState.value = ViewState.Error()
                        }
                    }
                }
        }
    }

    fun getUserDetail(userId: String) {
        viewModelScope.launch {
            userUseCase.getUserDetail(userId)
                .onStart {
                    _userState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _userState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _userState.value = ViewState.Success(result.data, result.responseCode, result.message)
                        }

                        is BaseResult.Error -> {
                            _userState.value = ViewState.Error()
                        }
                    }
                }
        }
    }

    fun createUser(userDto: UserDetailDto) {
        viewModelScope.launch {
            userUseCase.createUser(userDto)
                .onStart {
                    _userState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _userState.value = ViewState.Error(message = exception.message)
                    Log.e("CREATE_USER_CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _userState.value = ViewState.Success(result.data, result.responseCode, result.message)
                        }
                        is BaseResult.Error -> {
                            _userState.value = ViewState.Error()
                        }
                    }
                }
        }
    }

    fun updateUser(id: Int?, userDto: UserDetailDto) {
        viewModelScope.launch {
            userUseCase.updateUser(id, userDto)
                .onStart {
                    _updateState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _updateState.value = ViewState.Error(message = exception.message)
                    Log.e("UPDATE_USER_CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _updateState.value = ViewState.Success(result.data, result.responseCode, result.message)
                        }
                        is BaseResult.Error -> {
                            _updateState.value = ViewState.Error()
                        }
                    }
                }
        }
    }
    fun deleteUser(id: Int?) {
        viewModelScope.launch {
            userUseCase.deleteUser(id)
                .onStart {
                    _deleteState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _deleteState.value = ViewState.Error(message = exception.message)
                    Log.e("DELETE_USER_CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _deleteState.value = ViewState.Success(result.data, result.responseCode, result.message)
                        }
                        is BaseResult.Error -> {
                            _deleteState.value = ViewState.Error()
                        }
                    }
                }
        }
    }
}