package com.example.memodb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MemoData::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun memoDao(): MemoDao

    companion object {
        private var appDB: AppDataBase? = null

        @Synchronized
        fun getInstance(context: Context): AppDataBase? {
            if(appDB == null) {
                synchronized(AppDataBase::class.java) {
                    appDB = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "app-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return appDB
        }
    }
}