package edu.frank.androidStudy.http


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<T>(val code: Int, var status: Status, var data: T?, val message: String,val uniqueId: String = "") {
    companion object {
        fun <T> success(code: Int, message: String, data: T,uniqueId:String = ""): Resource<T> {
            return Resource(
                code,
                Status.SUCCESS,
                data,
                message,
                uniqueId
            )
        }

        fun <T> error(code: Int, msg: String, data: T?,uniqueId: String = ""): Resource<T> {
            return Resource(
                code,
                Status.ERROR,
                data,
                msg,
                uniqueId
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                0,
                Status.LOADING,
                data,
                "未知错误"
            )
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

