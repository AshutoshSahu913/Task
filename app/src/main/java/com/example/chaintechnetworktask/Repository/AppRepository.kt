package com.example.chaintechnetworktask.Repository

import androidx.lifecycle.LiveData
import com.example.chaintechnetworktask.DataSource.Room.SavedPasswordDao
import com.example.chaintechnetworktask.DataSource.Room.SavedPasswordEntity

class AppRepository(val savedPasswordDao: SavedPasswordDao) {

    suspend fun insertPassword(savedPassword: SavedPasswordEntity) =
        savedPasswordDao.insertData(savedPassword = savedPassword)

    fun getSavedPassword(): LiveData<List<SavedPasswordEntity>> =
        savedPasswordDao.getSavedPassword()

    suspend fun deleteSavedPassword(passwordId: Int) =
        savedPasswordDao.deleteSavedPassword(passwordId = passwordId)

    suspend fun updateSavedPassword(
        savedPassword: SavedPasswordEntity
    ) =
        savedPasswordDao.updateSavedPassword(
            savedPassword=savedPassword
        )
}