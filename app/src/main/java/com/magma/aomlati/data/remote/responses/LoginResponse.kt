package com.magma.aomlati.data.remote.responses

import com.google.gson.annotations.SerializedName


data class LoginResponse(

    @SerializedName("role")
    val role: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("expireIn")
    val expireIn: String

)

