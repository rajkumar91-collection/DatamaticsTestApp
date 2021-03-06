package com.rajkumar.datamaticsapp.utils

import androidx.lifecycle.LiveData
import com.rajkumar.datamaticsapp.model.api.ApiCallBack
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class LiveDataAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }


        val observableType =
            getParameterUpperBound(0, returnType as ParameterizedType)


        val rawObservableType: Type =
            getRawType(observableType)
        require(!(rawObservableType !== ApiCallBack::class.java)) { "Type must be a defined resource" }


        require(observableType is ParameterizedType) { "resource must be parameterized" }

        val bodyType =
            getParameterUpperBound(0, observableType)
        return LiveDataAdapter<Type>(bodyType)
    }
}