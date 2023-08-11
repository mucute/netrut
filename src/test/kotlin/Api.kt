import cn.mucute.netrut.annotation.BaseUrl
import cn.mucute.netrut.annotation.CoroutineContexts
import cn.mucute.netrut.annotation.Scope
import cn.mucute.netrut.annotation.Variable
import cn.mucute.netrut.annotation.request.Get
import cn.mucute.netrut.annotation.request.Post
import cn.mucute.netrut.builder.impl.Data
import java.util.Date

@BaseUrl("https://www.baidu.com")
interface Api {
    @Post("")
    @Scope(CoroutineContexts.Main)
    fun test(): Data<String>
}