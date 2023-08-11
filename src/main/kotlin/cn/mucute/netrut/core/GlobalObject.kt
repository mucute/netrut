package cn.mucute.netrut.core

import kotlin.reflect.KClass

object GlobalObject {
    var objectMap = mutableMapOf<KClass<out Any>, Any>()
}