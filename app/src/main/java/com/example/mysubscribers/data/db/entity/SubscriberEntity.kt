package com.example.mysubscribers.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//passo 1 do room
@Parcelize
@Entity(tableName = "subscriber")
data class SubscriberEntity (

    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,

    val name : String,

    val email: String

        ) : Parcelable