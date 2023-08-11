package cn.mucute.netrut.core

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

data class Test(var a: String)

class ParameterMap {
    //参数表
    private val mParamMap = LinkedHashMap<String, Any>()

    fun add(key: String, value: Any) {
        mParamMap[key] = value
    }

    fun getFormBody() = FormBody.Builder().apply {
        mParamMap.forEach { (key, value) ->
            this.add(key, value.toString())
        }
    }

    fun getRequestBody(): RequestBody {
        return Gson().toJson(mParamMap.entries.first().value).toRequestBody("application/json".toMediaType())
    }

    fun getStringParam() = StringBuffer().apply {
        mParamMap.onEachIndexed { index, map ->
            append(map.key)
            append("=")
            append(map.value)
            if (index != mParamMap.size - 1) append("&")
        }
    }.toString()

    fun isEmpty()  = mParamMap.isEmpty()
}

fun main() {
    var toJson = Gson().toJson(Test("aaaaaaaaa") as Any)
    print(toJson)
}