package edu.frank.androidStudy.http

import androidx.lifecycle.LiveData
import com.google.gson.internal.`$Gson$Types`.getRawType
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        val responseType:Type
        if (rawObservableType == Response::class.java) {
            if (observableType !is ParameterizedType) {
                throw IllegalStateException("Response must be parameterized" + " as Response<Foo> or Response<? extends Foo>")
            }
            responseType = getParameterUpperBound(0, observableType)
        } else if (rawObservableType == Result::class.java) {
            if (observableType !is ParameterizedType) {
                throw IllegalStateException("Result must be parameterized" + " as Result<Foo> or Result<? extends Foo>")
            }
            responseType = getParameterUpperBound(0, observableType)
        } else {
            responseType = observableType
        }

        return LiveDataCallAdapter<Any>(responseType)
    }
}
