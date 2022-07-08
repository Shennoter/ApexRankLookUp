package utils

import okhttp3.OkHttpClient
import okhttp3.Request
import pers.shennoter.Config
import java.net.InetSocketAddress

fun getRes(url: String):Pair<Int,String?>{
    val builder = OkHttpClient.Builder()
    val type = when(Config.proxyType){
        "HTTP" -> java.net.Proxy.Type.HTTP
        "SOCKS" -> java.net.Proxy.Type.SOCKS
        else -> return Pair(1, "代理设置错误")
    }
    if(Config.ifProxy) {
        val socket = InetSocketAddress(Config.hostName, Config.port) //构造套接字
        val proxy = java.net.Proxy(type, socket)
        builder.proxy(proxy) //构造代理
    }
    val client = builder //构造client
        .build()
    val request: Request = Request.Builder() //构造request
        .url(url)
        .get()
        .build()
    val response = try {
        client.newCall(request).execute()
    }
    catch (e:Exception){
        e.printStackTrace()
        return Pair(1, "网络请求发起错误:${e.message}")
    }
    val body = response.body?.string()
    response.close()
    return when(response.code){
        200 -> {
            if (body?.contains("Error") == true) {
                Pair(1,"错误：" + body.split("\"")[3]) //{"Error":"Player /apexmap not found (code 103 - skipping origin backup api)"} 总有人会输入一些意料之外的数据
            } else {
                Pair(0, body)
            }
        }
        400 -> Pair(1, "请重试")
        403 -> Pair(1, "API key无权限或不存在")
        404 -> Pair(1, "玩家不存在")
        405 -> Pair(1, "玩家存在但从未玩过APEX")
        410 -> Pair(1, "未知平台")
        429 -> Pair(1, "API过热，请稍后再试")
        500 -> Pair(1, "API服务器内部错误")
        else -> Pair(1, "未知错误")
    }
}