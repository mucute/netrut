import cn.mucute.netrut.core.Netrut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    Netrut.create<Api>().test().success {
        println(it)
    }.error {
        println(it)
    }
    Thread.sleep(5000L)
}