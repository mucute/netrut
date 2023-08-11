package cn.mucute.netrut.core

import kotlin.reflect.KClass
import kotlin.reflect.KParameter

/**
 *
 */
class ParameterUtils {
    private val parameterList = mutableListOf<Parameter>()
    fun filterVariable(method: List<KParameter>, args: Array<Any>) {

    }
}

class Parameter {
    private lateinit var name: String;
    private var annotationList = mutableListOf<KClass<out Any>>()
}