package com.rajkumar.datamaticsapp.viewmodel

import androidx.lifecycle.*
import com.rajkumar.datamaticsapp.model.Donut
import com.rajkumar.datamaticsapp.model.api.Resource
import com.rajkumar.datamaticsapp.model.api.Status
import com.rajkumar.datamaticsapp.model.repository.DataRepository


class DonutListViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val donuts: MediatorLiveData<Resource<List<Donut?>?>?> =
        MediatorLiveData()


    fun getDonuts(): MediatorLiveData<Resource<List<Donut?>?>?> {
        return donuts
    }

    fun getDonutList() {
        searchDonut()
    }


    private fun searchDonut() {
        val repositorySource: LiveData<Resource<List<Donut?>?>?> =
            dataRepository.getDonutListApi()
        donuts.addSource(
            repositorySource,
            Observer {
                if (it != null) {
                    if (it.status == Status.SUCCESS) {

                        if (it.data != null) {
                            if (it.data.isEmpty()) {
                                donuts.value = Resource(
                                    it.data as List<Donut?>?,
                                    "No data found",
                                    Status.ERROR
                                )
                                donuts.removeSource(repositorySource)
                            }

                        }


                    } else if (it.status == Status.ERROR) {
                        donuts.removeSource(repositorySource)

                    }
                    donuts.value = it


                } else {
                    donuts.removeSource(repositorySource)
                }


            })
    }




}

class DonutViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonutListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonutListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

