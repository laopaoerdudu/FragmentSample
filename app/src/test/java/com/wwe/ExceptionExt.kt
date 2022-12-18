package com.wwe

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/** 不需要写 CoroutineExceptionHandler 局部变量，更为优雅简洁。 */
fun <T> CoroutineScope.rxLaunch(init: CoroutineCallBack<T>.() -> Unit) {
    val result = CoroutineCallBack<T>().apply(init)
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        result.onError?.invoke(exception)
    }
    launch(coroutineExceptionHandler) {
        val res: T? = result.onRequest?.invoke()
        res?.let {
            result.onSuccess?.invoke(it)
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
//        resposity.getData()
//    }
//    onSuccess = {
//        //成功回调
//    }
//    onError = {
//        //失败回调
//    }
//}