package cn.mucute.netrut.core

import cn.mucute.netrut.annotation.BaseUrl
import java.lang.reflect.Proxy

object Netrut {
    /**
     * 默认实例化方法
     */
    inline fun <reified T : Any> create(): T {
        if (!T::class.java.isAnnotationPresent(BaseUrl::class.java)) {
            throw Exception("no RequestMapping")
        }
        if (!GlobalObject.objectMap.containsKey(T::class)) {
            GlobalObject.objectMap[T::class] = Proxy.newProxyInstance(
                T::class.java.classLoader,
                arrayOf(T::class.java),
                MethodProxy(T::class.java.getAnnotation(BaseUrl::class.java).url, T::class)
            )
        }
        return GlobalObject.objectMap[T::class] as T
    }

    /**
     * 使用此实例化方法不必配置@BaseUrl注解
     */
    inline fun <reified T : Any> create(url: String): T {
        return Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf(T::class.java),
            MethodProxy<T>(url, T::class)
        ) as T
    }



}