package edu.frank.androidStudy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import edu.frank.androidStudy.http.BaseResponse
import edu.frank.androidStudy.http.Resource
import kotlinx.coroutines.*
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask
import java.util.concurrent.ThreadPoolExecutor
import javax.inject.Inject

class ListRequestModel @Inject constructor(private val service: ApiService) {

    fun updateStatus(position: Int): LiveData<Resource<String>> {
        return object : NetworkBoundResource<String>() {
            override fun createCall(): LiveData<BaseResponse<String>> {
                return service.updateStatus2(position)
            }
        }.asLiveData()
    }
}