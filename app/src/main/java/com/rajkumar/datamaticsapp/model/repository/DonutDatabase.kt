package com.rajkumar.datamaticsapp.model.repository

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rajkumar.datamaticsapp.model.Donut


@Database(entities = [Donut::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
public abstract class DonutDatabase : RoomDatabase() {
    abstract fun movieDao(): DonutDao



    companion object {
        const val DATABASE_NAME = "donut_db"
        @Volatile
        private var INSTANCE: DonutDatabase? = null

        fun getDatabase(context: Context): DonutDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DonutDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(MovieDatabaseCallback())
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

    private class MovieDatabaseCallback(
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                /*We can perform initial db operation here*/
            }
        }


    }
}

