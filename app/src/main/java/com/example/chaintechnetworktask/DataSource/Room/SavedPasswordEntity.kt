package com.example.chaintechnetworktask.DataSource.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chaintechnetworktask.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class SavedPasswordEntity(
    @PrimaryKey
    val id: Int? = null,
    var accountName: String? = null,
    var userName_Email: String? = null,
    var password: String? = null
)
