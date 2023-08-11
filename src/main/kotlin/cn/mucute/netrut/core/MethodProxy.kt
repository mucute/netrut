package cn.mucute.netrut.core

import cn.mucute.netrut.annotation.parser.Downloader
import cn.mucute.netrut.builder.DataBuilder
import cn.mucute.netrut.builder.FileDownloadBuilder
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.Throws
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation

/**
 * @author muc
 * @CreateData 2022/5/15
 */
class MethodProxy<T : Any>(private val url: String, private val kClass: KClass<T>) : InvocationHandler {
    @Throws(Throwable::class)
    override fun invoke(proxy: Any, method: Method, args: Array<Any>?): Any {
        if (kClass.hasAnnotation<Downloader>()){
            return FileDownloadBuilder<T>()
        }
        return DataBuilder<T>()
            .setMethod(method)
            .setProxyClass(kClass)
            .setArguments(args)
            .setBaseUrl(url)
            .build()
    }
}