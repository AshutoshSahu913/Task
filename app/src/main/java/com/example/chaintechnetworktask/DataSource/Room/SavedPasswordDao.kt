package com.example.chaintechnetworktask.DataSource.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SavedPasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(savedPassword: SavedPasswordEntity)


    @Query("SELECT * FROM SavedPassword")
    fun getSavedPassword(): LiveData<List<SavedPasswordEntity>>

    @Query("DELETE FROM SavedPassword Where id = :passwordId")
    suspend fun deleteSavedPassword(passwordId: Int)

    @Update
    suspend fun updateSavedPassword(
        savedPassword: SavedPasswordEntity
    )

}