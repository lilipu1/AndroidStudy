package edu.frank.androidStudy.http


import android.util.Log
import androidx.lifecycle.LiveData
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 * @param <R>
</R> */
class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<BaseResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<BaseResponse<R>> {
        return object : LiveData<BaseResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            var uniqueId:String? = null
                            val formBody = call.request().body() as FormBody
                            for (i in 0 until formBody.size()) {
                                val name = formBody.name(i)
                                val value = formBody.value(i).toString()
                                if (name == "position"){
                                    uniqueId = value
                                }
                            }
                            if (uniqueId != null){
                                postValue(BaseResponse.create(response,uniqueId))
                                Log.e("发送",uniqueId)
                            }else{
                                postValue(BaseResponse.create(response))
                            }
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(BaseResponse.create(throwable))
                        }
                    })
                }
            }
        }
    }
}
