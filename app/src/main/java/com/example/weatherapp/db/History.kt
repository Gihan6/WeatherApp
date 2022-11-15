package com.example.weatherapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "History")

data class History(  @PrimaryKey(autoGenerate = true)
                     @ColumnInfo(name = "id")
                     @SerializedName("id")
                     var id: Int=0,
                     @ColumnInfo(name = "cityId")
                     @SerializedName("cityId")
                     var cityId: Int,
                     @SerializedName("time")
                     var time: String,
                     @SerializedName("desc")
                     var desc: String,
                     @SerializedName("temp")
                     var temp: String

)
