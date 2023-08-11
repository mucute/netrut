package cn.mucute.netrut

import cn.mucute.netrut.annotation.request.Get
import cn.mucute.netrut.annotation.request.Post
import cn.mucute.netrut.parser.Parser
import java.lang.reflect.Method
import kotlin.reflect.KClass

abstract class BaseBuilder<T> {
    //当前调用的方法
    lateinit var method: Method

    //方法参数的值
    var arguments: Array<Any>? = null

    //基础URl
    lateinit var baseUrl: String

    //通过Netrut代理的类
    lateinit var proxyClass: KClass<out Any>

    //程序执行模式
    var parser = Parser.NONE
    fun setBaseUrl(baseUrl: String): BaseBuilder<T> {
        this.baseUrl = baseUrl
        when {
            method.isAnnotationPresent(Get::class.java) -> {
                this.baseUrl += method.getAnnotation(Get::class.java).url
            }

            method.isAnnotationPresent(Post::class.java) -> {
                this.baseUrl += method.getAnnotation(Post::class.java).url
            }
        }
        return this
    }


    fun setMethod(method: Method): BaseBuilder<T> {
        this.method = method
        return this
    }

    fun setProxyClass(kClazz: KClass<out Any>): BaseBuilder<T> {
        proxyClass = kClazz
        return this
    }

    fun setArguments(rgs: Array<Any>?): BaseBuilder<T> {
        this.arguments = rgs
        return this
    }

    abstract fun build(): Any
}