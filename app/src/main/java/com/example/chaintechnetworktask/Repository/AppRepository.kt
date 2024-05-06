package com.example.chaintechnetworktask.Repository

import com.example.chaintechnetworktask.DataSource.Room.SavedPasswordDao
import com.example.chaintechnetworktask.DataSource.Room.SavedPasswordEntity

class AppRepository(val savedPasswordDao: SavedPasswordDao) {

    suspend fun insertPassword(savedPassword: SavedPasswordEntity) =
        savedPasswordDao.insertData(savedPassword = savedPassword)
}