package com.rajkumar.datamaticsapp

import android.app.Application
import com.rajkumar.datamaticsapp.model.repository.DataRepository
import com.rajkumar.datamaticsapp.model.repository.DonutDatabase

class DonutsApplication : Application() {

    val database by lazy { DonutDatabase.getDatabase(this) }
    val repository by lazy { DataRepository(database.movieDao()) }
}