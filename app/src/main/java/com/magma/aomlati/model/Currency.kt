package com.magma.aomlati.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.magma.aomlati.model.converters.TitleConverter
import com.magma.aomlati.model.converters.ListValueConverter

@Entity(tableName = "currency")
class Currency(

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

    @ColumnInfo(name = "image")
    @SerializedName("image")
    var image: String?,

    @ColumnInfo(name = "type")
    @SerializedName("type")
    var type: String?,

    @ColumnInfo(name = "lastPrice")
    @SerializedName("lastPrice")
    var lastPrice: Double?,

    @ColumnInfo(name = "lastPriceUpdate")
    @SerializedName("lastPriceUpdate")
    var lastPriceUpdate: String?,

    @ColumnInfo(name = "changePercent")
    @SerializedName("changePercent")
    var changePercent: Double?,

    @TypeConverters(ListValueConverter::class)
    @SerializedName("latest5")
    @ColumnInfo(name = "latest5")
    var latest5: List<Value>?,

    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt")
    var createdAt: String?,

    @ColumnInfo(name = "modifiedAt")
    @SerializedName("modifiedAt")
    var modifiedAt: String?,

    @ColumnInfo(name = "isFavorite", defaultValue = "false")
    var isFavorite: Boolean

)
