package cn.mucute.netrut.builder.impl

import cn.mucute.netrut.annotation.request.Get
import cn.mucute.netrut.annotation.request.Post
import cn.mucute.netrut.annotation.RequestBody
import cn.mucute.netrut.core.NetrutExecute
import cn.mucute.netrut.core.ParameterMap
import cn.mucute.netrut.parser.Parser
import com.google.gson.Gson

import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType


class Data<T>(
    private var url: String,
    private var method: Method,
    private var paramMap: ParameterMap,
    private var parser: Parser
) {
    private var success: ((T) -> Unit)? = null
    private var error: ((IOException) -> Unit)? = null
    private val netrutExecute = NetrutExecute(method)
    private val builder: Request.Builder = Request.Builder().apply {
        when {
            method.isAnnotationPresent(Get::class.java) -> {
                if (paramMap.isEmpty()) {
                    url(url)
                } else {
                    url(url + "?" + paramMap.getStringParam())
                }
                get()
            }

            method.isAnnotationPresent(Post::class.java) -> {
                url(url)
                when {
                    method.isAnnotationPresent(RequestBody::class.java) -> {
                        post(paramMap.getRequestBody())
                    }

                    else -> {
                        post(paramMap.getFormBody().build())
                    }
                }
            }

            else -> {
                throw Exception("You must configure the request method")
            }
        }
    }

    init {
        netrutExecute.success = {
            success?.invoke(getResponseType(it))
        }
        netrutExecute.error = {
            error?.invoke(it)
        }

    }

    fun execute(): T {
        netrutExecute.request = builder.build()
        return getResponseType(netrutExecute.execute())
    }

    fun success(res: (T) -> Unit): Data<T> {
        netrutExecute.request = builder.build()
        netrutExecute.start()
        success = res
        return this
    }

    fun error(error: (IOException) -> Unit) {
        this.error = error
    }

    private fun getResponseType(response: Response): T {
        val string = response.body!!.string()
        val message = method.genericReturnType as ParameterizedType
        return when (parser) {
            Parser.GSON -> {
                return Gson().fromJson(string, message.actualTypeArguments[0])
            }

            Parser.NONE -> {
                return string as T;
            }

            Parser.DOWNLOAD -> TODO()
        }
    }

}

data class Test(val a: String)
