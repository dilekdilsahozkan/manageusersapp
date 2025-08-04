package com.dilsahozkan.manageusersapp.data.remote.dto

data class AddressDto(
    val street : String? = null,
    val suite : String? = null,
    val city : String? = null,
    val zipcode : String? = null,
    val geo : GeoDto? = null
)

data class GeoDto(
    val lat : String? = null,
    val lng : String? = null
)