package com.dilsahozkan.manageusersapp.data.remote.dto

data class UserDetailDto (
    val id : Int? = null,
    val name : String? = null,
    val username : String? = null,
    val email : String? = null,
    val address : AddressDto? = null,
    val phone : String? = null,
    val website : String? = null,
    val company : CompanyDto? = null,
    val errorMessage: String? = null,
)