package com.rajkumar.datamaticsapp.model.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.rajkumar.datamaticsapp.model.Donut


@Dao
interface DonutDao {


    @Insert(onConflict = IGNORE)
    fun insertDonut(donut: List<Donut?>?): LongArray?


    @Query("SELECT * FROM donuts")
    fun searchDonut(
    ): LiveData<List<Donut?>?>


}