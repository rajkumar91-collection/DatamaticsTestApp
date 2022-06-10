package com.rajkumar.datamaticsapp.model.api

import androidx.lifecycle.LiveData
import com.rajkumar.datamaticsapp.model.Donut
import retrofit2.http.GET


interface IWebserviceInterface {


    @GET("0ba63b71-bb15-434e-9da3-98435dcb346d")
    fun searchMovie(
    ): LiveData<ApiCallBack<List<Donut>?>>



}