package com.example.getitdone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class GetItDoneDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        fun createDatabase(context: Context): GetItDoneDatabase =
            Room.databaseBuilder(context, GetItDoneDatabase::class.java, "get_it_done_database")
                .build()
    }
}

