package com.example.chaintechnetworktask.DataSource.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "SavedPassword")
data class SavedPasswordEntity(
    @PrimaryKey
    val id: Int? = null,
    var accountName: String? = null,
    var userName_Email: String? = null,
    var password: String? = null
) : Serializable
