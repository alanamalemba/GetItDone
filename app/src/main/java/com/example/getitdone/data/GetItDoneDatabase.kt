package com.example.getitdone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 2)
abstract class GetItDoneDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {

        @Volatile
        private var DATABASE_INSTANCE: GetItDoneDatabase? = null

        fun getDatabaseInstance(context: Context): GetItDoneDatabase {

            return DATABASE_INSTANCE ?: synchronized(this) {
                val dbInstance = Room.databaseBuilder(
                    context, GetItDoneDatabase::class.java, "get_it_done_database"
                ).fallbackToDestructiveMigration(true).build()

                DATABASE_INSTANCE = dbInstance
                return dbInstance

            }
        }
    }
}

