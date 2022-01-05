package com.magma.aomlati.model

import com.google.gson.annotations.SerializedName

class Image(

    @SerializedName("id")
    var id: Long,

    @SerializedName("path")
    var path: String?,

)
