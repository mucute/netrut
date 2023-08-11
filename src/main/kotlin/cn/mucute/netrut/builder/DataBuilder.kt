package cn.mucute.netrut.builder

import cn.mucute.netrut.BaseBuilder
import cn.mucute.netrut.annotation.Param
import cn.mucute.netrut.annotation.Variable
import cn.mucute.netrut.annotation.parser.Downloader
import cn.mucute.netrut.annotation.parser.GsonParser
import cn.mucute.netrut.builder.impl.Data
import cn.mucute.netrut.core.ParamChecker
import cn.mucute.netrut.core.ParameterMap
import cn.mucute.netrut.parser.Parser
import kotlin.reflect.full.*

class DataBuilder<T> : BaseBuilder<T>() {

    override fun build(): Data<T> {
        //获取类绑定类型
        parser = when {
            proxyClass.hasAnnotation<GsonParser>() -> {
                Parser.GSON
            }

            proxyClass.hasAnnotation<Downloader>() -> {
                Parser.DOWNLOAD
            }

            else -> {
                Parser.NONE
            }
        }
        if (ParamChecker.existsFileType(method.parameterTypes)) {
            println("FIle")
        }
        val paramBuilder = ParameterMap().apply {
            if (arguments == null) {
                return@apply
            }
            if (arguments?.get(0) is Map<*, *>) {
                val map = arguments!![0] as Map<*, *>
                map.forEach { (any, u) ->
                    add(any.toString(), u!!)
                }
            } else {
                proxyClass.memberFunctions.find {
                    it.name == method.name
                }.also {
                    it!!.valueParameters.forEachIndexed { index, kParameter ->
                        //如果存在Param注解则使用Param的Name
                        when {
                            kParameter.hasAnnotation<Param>() -> {
                                add(
                                    kParameter.findAnnotation<Param>()!!.name,
                                    arguments!![index].toString()
                                )
                            }

                            kParameter.hasAnnotation<Variable>() -> {
                                baseUrl= baseUrl.replace("{"+kParameter.name!!.toString()+"}", arguments!![index].toString())
                            }

                            else -> add(kParameter.name!!, arguments!![index])
                        }
                    }
                }
            }
        }
        return Data(baseUrl, method, paramBuilder, parser)
    }
}