package com.example.chaintechnetworktask.DataSource.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chaintechnetworktask.DATABASE_NAME
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [SavedPasswordEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedPasswordDao(): SavedPasswordDao

    @InternalCoroutinesApi
    companion object {

        @Volatile
        var Instance: AppDatabase? = null

        fun getDatabaseInstance(context: Context): AppDatabase {
            val tempInstance = Instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val roomDB = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration().build()
                return roomDB
            }
        }
    }
}