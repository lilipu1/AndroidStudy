package edu.frank.androidStudy

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import edu.frank.androidStudy.http.BaseResponse
import edu.frank.androidStudy.http.Resource
import kotlinx.coroutines.*

abstract class NetworkBoundResource<ResultType>
@MainThread constructor() {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        if (dbSource.value == null) {
            fetchFromNetwork(dbSource)
        } else {
            result.addSource(dbSource) { t ->
                result.removeSource(dbSource)
                if (shouldFetch(t)) {
                    fetchFromNetwork(dbSource)
                } else {
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.success(200, "", newData))
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val baseResponse: LiveData<BaseResponse<ResultType>> = createCall()
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(baseResponse) { response ->
            result.removeSource(baseResponse)
            result.removeSource(dbSource)
            when (response?.code) {
                200 -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        saveCallResult(response.data)
                        val fakeResult = MutableLiveData<ResultType>()
                        fakeResult.value = response.data
                        result.addSource(fakeResult) { newData ->
                            setValue(Resource.success(200, response.message, newData,response.uniqueId?:""))
                        }
                    }
                }

                else -> {
                    onFetchFailed(response?.message ?: "空")
                    result.addSource(dbSource) { newData ->
                        setValue(
                            Resource.error(
                                response?.code?: -1, response?.message
                                    ?: "", newData,response.uniqueId?:""
                            )
                        )

                    }
                }
            }
        }
    }


    protected open fun onFetchFailed(errorMessage: String) {}

    //用于单一请求或合并请求第一个请求之外的请求
    fun asLiveData() = result as LiveData<Resource<ResultType>>

    //用于合并请求的第一个请求
    fun asMediatorLiveData() = result

    /**
     * 请求失败时是否显示失败的toast提示,默认需要
     * */
    protected open fun shouldShowToast(): Boolean = true

    /**
     * 缓存数据方法,需要缓存时复写该方法
     * */
    @WorkerThread
    protected open suspend fun saveCallResult(item: ResultType?) = withContext(Dispatchers.IO) {

    }

    /**
     * 是否需要从网络获取数据,默认需要
     * */
    @MainThread
    protected open fun shouldFetch(data: ResultType?): Boolean = true

    @MainThread
    protected open fun loadFromDb(): LiveData<ResultType> {
        val fakeData = MutableLiveData<ResultType>()
        fakeData.value = null
        return fakeData
    }

    @MainThread
    protected abstract fun createCall(): LiveData<BaseResponse<ResultType>>
}
