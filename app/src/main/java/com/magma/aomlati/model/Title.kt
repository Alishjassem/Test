package com.magma.aomlati.model

import com.google.gson.annotations.SerializedName

class Title(

    @SerializedName("en")
    var en: String,

    @SerializedName("ar")
    var ar: String,

    @SerializedName("tr")
    var tr: String,
)