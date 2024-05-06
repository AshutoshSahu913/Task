package com.example.chaintechnetworktask.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.chaintechnetworktask.DataSource.Room.AppDatabase
import com.example.chaintechnetworktask.DataSource.Room.SavedPasswordEntity
import com.example.chaintechnetworktask.Repository.AppRepository
import kotlinx.coroutines.InternalCoroutinesApi

class MainViewModel(application: Application) : AndroidViewModel(application) {

    @OptIn(InternalCoroutinesApi::class)
    val savedPasswordDao = AppDatabase.getDatabaseInstance(application).savedPasswordDao()

    val appRepository = AppRepository(savedPasswordDao)
    suspend fun insertPassword(savedPassword: SavedPasswordEntity) =
        appRepository.insertPassword(savedPassword = savedPassword)
}