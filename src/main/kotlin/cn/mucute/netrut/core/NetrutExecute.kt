package cn.mucute.netrut.core

import cn.mucute.netrut.annotation.Scope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Method

class NetrutExecute(private val method: Method) {

    private val okHttpClient: OkHttpClient = OkHttpClient()
    lateinit var request: Request
    var success: ((Response) -> Unit)? = null
    var error: ((IOException) -> Unit)? = null


    fun execute() =
        okHttpClient.newCall(request).execute()


    fun start() {
        val value = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (method.isAnnotationPresent(Scope::class.java)) {
                    CoroutineScope(method.getAnnotation(Scope::class.java).context.context).launch {
                        error?.invoke(e)
                    }
                } else {
                    error?.invoke(e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (method.isAnnotationPresent(Scope::class.java)) {
                    CoroutineScope(method.getAnnotation(Scope::class.java).context.context).launch {
                        success?.invoke(response)
                    }
                } else {
                    success?.invoke(response)
                }
            }
        }
        okHttpClient.newCall(request).enqueue(value)
    }

    fun coroutineInvoke(block: () -> Unit) {
        if (method.isAnnotationPresent(Scope::class.java)) {
            CoroutineScope(method.getAnnotation(Scope::class.java).context.context).launch {
                block()
            }
        }
    }
}