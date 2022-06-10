package com.rajkumar.datamaticsapp.model.api

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

abstract class NetworkBoundResource<CacheObject, RequestObject>(private val appExecutors: AppExecutors?) {
    private val results: MediatorLiveData<Resource<CacheObject?>?> = MediatorLiveData()
    private fun init() {

        results.value = Resource.Loading("Loading...", null)
        val dbSource = loadFromDb()
        results.addSource(dbSource) { cacheObject ->
            results.removeSource(dbSource)
            if (shouldFetch(cacheObject)) {
                fetchFromNetwork(dbSource)
            } else {
                results.addSource(dbSource) {
                    setValue(Resource.Success("Success", it))

                }
            }
        }


    }

    private fun fetchFromNetwork(dbSource: LiveData<CacheObject?>) {
        results.addSource(dbSource) {
            setValue(Resource.Loading("Loading...", it))

        }
        val apiResponse: LiveData<ApiCallBack<RequestObject?>> = createCall()
        results.addSource(apiResponse) { api ->
            results.removeSource(dbSource)
            results.removeSource(apiResponse)
            when (api) {
                is ApiCallBack<*>.ApiSuccessCallBack<*> -> {
                    appExecutors?.diskIO()?.execute {
                        saveCallResult(processResponse(api as ApiCallBack<RequestObject>.ApiSuccessCallBack<RequestObject>))
                        appExecutors.mainThread().execute(Runnable {
                            results.addSource(loadFromDb()) {

                                setValue(Resource.Success("Success", it))

                            }
                        })
                    }
                }
                is ApiCallBack<*>.ApiEmptyCallBack<*> -> {
                    Log.d(
                        TAG,
                        "onChanged: ApiEmptyResponse"
                    )
                    appExecutors?.mainThread()?.execute(Runnable {
                        results.addSource(loadFromDb(), Observer {

                            setValue(Resource.Success("Success", it))

                        })
                    })
                }
                is ApiCallBack<*>.ApiErrorCallBack<*> -> {

                    results.addSource(dbSource, Observer {

                        setValue(
                            Resource.Error(api.errorMessage ?: "Success", it)
                        )


                    })
                }
            }

        }
    }

    private fun processResponse(response: ApiCallBack<RequestObject>.ApiSuccessCallBack<RequestObject>): RequestObject? {
        return response.body
    }

    private fun setValue(newValue: Resource<CacheObject?>?) {
        if (results.value !== newValue) {
            results.value = newValue
        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract fun saveCallResult(@NonNull response: RequestObject?)

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract fun shouldFetch(@Nullable data: CacheObject?): Boolean

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): LiveData<CacheObject?>

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract fun createCall(): LiveData<ApiCallBack<RequestObject?>>

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    val asLiveData: LiveData<Resource<CacheObject?>?>
        get() = results

    companion object {
        private const val TAG: String = "NetworkBoundResource"
    }

    init {
        init()
    }
}