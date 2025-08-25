package com.example.getitdone.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.getitdone.data.model.Task
import com.example.getitdone.data.model.TasksList

@Database(entities = [Task::class, TasksList::class], version = 4)
abstract class GetItDoneDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao
    abstract fun getTasksListDao(): TasksListDao

    companion object {

        @Volatile
        private var DATABASE_INSTANCE: GetItDoneDatabase? = null

        private val MIGRATION_2_TO_3 = object : Migration(2, 3) {

            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS tasks_lists (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                         name TEXT NOT NULL
                    )
                """.trimMargin()
                )
            }
        }

        fun getDatabaseInstance(context: Context): GetItDoneDatabase {

            return DATABASE_INSTANCE ?: synchronized(this) {
                val dbInstance = Room.databaseBuilder(
                    context, GetItDoneDatabase::class.java, "get_it_done_database"
                ).addMigrations(MIGRATION_2_TO_3).fallbackToDestructiveMigration(true).build()

                DATABASE_INSTANCE = dbInstance
                return dbInstance

            }
        }
    }
}

