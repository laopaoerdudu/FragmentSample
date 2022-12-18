package com.wwe

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/** 不需要写 CoroutineExceptionHandler 局部变量，更为优雅简洁。 */
fun <T> CoroutineScope.rxLaunch(init: CoroutineCallBack<T>.() -> Unit) {
    val callback = CoroutineCallBack<T>().apply(init)
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        callback.onError?.invoke(exception)
    }
    launch(coroutineExceptionHandler) {
        val event: T? = callback.onRequest?.invoke()
        event?.let { result ->
            callback.onSuccess?.invoke(result)
        }
    }
}

class CoroutineCallBack<T> {
    var onRequest: (suspend () -> T)? = null
    var onSuccess: ((T) -> Unit)? = null
    var onError: ((Throwable) -> Unit)? = null
}

// How to apply?

//viewModelScope.rxLaunch<String> {
//    onRequest = {
//        //网络请求
//        repository.getData()
//    }
//    onSuccess = {
//        //成功回调
//    }
//    onError = {
//        //失败回调
//    }
//}