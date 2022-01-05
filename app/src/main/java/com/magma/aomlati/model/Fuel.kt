package com.magma.aomlati.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.magma.aomlati.model.converters.TitleConverter

@Entity(tableName = "fuel")
class Fuel(

    @PrimaryKey
    @SerializedName("_id")
    @ColumnInfo(name = "_id")
    var _id: String,

    @ColumnInfo(name = "symbol")
    @SerializedName("symbol")
    var symbol: String?,

    @TypeConverters(TitleConverter::class)
    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: Title?,

    @ColumnInfo(name = "changePercent")
    @SerializedName("changePercent")
    var changePercent: Double?,

    @ColumnInfo(name = "lastPrice")
    @SerializedName("lastPrice")
    var lastPrice: Double?,

    @ColumnInfo(name = "lastPriceUpdate")
    @SerializedName("lastPriceUpdate")
    var lastPriceUpdate: String?,

    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt")
    var createdAt: String?,

    @ColumnInfo(name = "modifiedAt")
    @SerializedName("modifiedAt")
    var modifiedAt: String?,

    @ColumnInfo(name = "isFavorite", defaultValue = "false")
    var isFavorite: Boolean

)
