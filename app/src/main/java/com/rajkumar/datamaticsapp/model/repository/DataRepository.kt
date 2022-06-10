package com.rajkumar.datamaticsapp.model.repository

import androidx.lifecycle.LiveData
import com.rajkumar.datamaticsapp.model.Donut
import com.rajkumar.datamaticsapp.model.api.*


class DataRepository(private val donutDao: DonutDao) {


    fun getDonutListApi(): LiveData<Resource<List<Donut?>?>?> {
        return object :
            NetworkBoundResource<List<Donut?>?, List<Donut>?>(AppExecutors) {
            override fun saveCallResult(response: List<Donut>?) {
                if (response != null) {
                    donutDao.insertDonut(response)


                }
            }

            override fun loadFromDb(): LiveData<List<Donut?>?> {
                return donutDao.searchDonut()
            }

            override fun createCall(): LiveData<ApiCallBack<List<Donut>?>> {
                return WebServiceController.getApiService().searchMovie()
            }


            override fun shouldFetch(data: List<Donut?>?): Boolean {
                return true
            }
        }.asLiveData
    }


}