package com.rajkumar.datamaticsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "donuts")
data class Donut(

    @field:SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @field:SerializedName("type")
    @ColumnInfo(name = "type")
    var type: String? ,

    @field:SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String? ,


    @field:SerializedName("topping")
    @ColumnInfo(name = "topping")
    var topping: List<Topping>



)






