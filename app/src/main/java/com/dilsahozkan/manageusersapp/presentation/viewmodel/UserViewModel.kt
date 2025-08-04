package com.dilsahozkan.manageusersapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dilsahozkan.manageusersapp.common.BaseResult
import com.dilsahozkan.manageusersapp.common.BaseViewModel
import com.dilsahozkan.manageusersapp.common.ViewState
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


    var _userState: MutableStateFlow<ViewState<UserDto>> =
        MutableStateFlow(ViewState.Idle())
    val userState: StateFlow<ViewState<UserDto>> = _userState

    fun getUserInfo() {
        viewModelScope.launch {
            userUseCase.getUserInfo()
                .onStart {
                    _userListState.value = ViewState.Idle()
                }
                .catch { exception ->
                    _userListState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _userListState.value = ViewState.Success(result.data)
                        }

                        is BaseResult.Error -> {
                            _userListState.value = ViewState.Error()
                        }
                    }
                }
        }
    }

    fun createUser(userDto: UserDto) {
        viewModelScope.launch {
            userUseCase.createUser(userDto)
                .onStart {
                    _userState.value = ViewState.Idle()
                }
                .catch { exception ->
                    _userState.value = ViewState.Error(message = exception.message)
                    Log.e("CREATE_USER_CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _userState.value = ViewState.Success(result.data)
                        }
                        is BaseResult.Error -> {
                            _userState.value = ViewState.Error()
                        }
                    }
                }
        }
    }

    fun updateUser(id: Int, userDto: UserDto) {
        viewModelScope.launch {
            userUseCase.updateUser(id, userDto)
                .onStart {
                    _userState.value = ViewState.Idle()
                }
                .catch { exception ->
                    _userState.value = ViewState.Error(message = exception.message)
                    Log.e("UPDATE_USER_CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _userState.value = ViewState.Success(result.data)
                        }
                        is BaseResult.Error -> {
                            _userState.value = ViewState.Error()
                        }
                    }
                }
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            userUseCase.deleteUser(id)
                .onStart {
                    _userState.value = ViewState.Idle()
                }
                .catch { exception ->
                    _userState.value = ViewState.Error(message = exception.message)
                    Log.e("DELETE_USER_CATCH", "exception : $exception")
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            _userState.value = ViewState.Success(result.data)
                        }
                        is BaseResult.Error -> {
                            _userState.value = ViewState.Error()
                        }
                    }
                }
        }
    }
}