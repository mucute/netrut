package cn.mucute.netrut.annotation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention()
annotation class Scope(val context: CoroutineContexts)
enum class CoroutineContexts(val context: CoroutineContext) {
    Main(Dispatchers.Main),
    Io(Dispatchers.IO),
    Default(Dispatchers.Default),
    Unconfined(Dispatchers.Unconfined)
}