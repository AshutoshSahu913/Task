package com.example.chaintechnetworktask.DataSource.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface SavedPasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(savedPassword: SavedPasswordEntity)



}