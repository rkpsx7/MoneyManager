package com.rkpsx7.moneymanager.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @ColumnInfo(name = "profile_img") var profile_img: Bitmap,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "email") var email: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}