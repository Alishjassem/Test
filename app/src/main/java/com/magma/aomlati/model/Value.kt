package com.magma.aomlati.model

import com.google.gson.annotations.SerializedName

class Value(

    @SerializedName("_id")
    var _id: String?,

    @SerializedName("value")
    var value: Double?,

    @SerializedName("createdAt")
    var createdAt: String?,
)