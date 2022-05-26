package pers.shennoter

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object Config : AutoSavePluginConfig("config") {
    @ValueDescription("apiKey：如果没有请到 https://apexlegendsapi.com/ 获取")
    val apiKey: String by value()
    @ValueDescription("额外apiKey：不同apiKey用英文逗号隔开,apiKey用英文单引号包裹，可不填")
    val extendApiKey: ArrayList<String> by value()
    @ValueDescription("回复模式: pic为图片，text为文字(适用于玩家信息、地图轮换和猎杀门槛)")
    var mode: String by value("pic")
    @ValueDescription("图片质量: PNG原图，JPG更小")
    var picType: String by value("PNG")
    @ValueDescription("缓存过期时间（单位：天）")
    var cacheExpireTime: Int by value(30)
    @ValueDescription("缓存自动清理：true为启用，false为关闭")
    var cacheAutoDel: Boolean by value(true)
    @ValueDescription("玩家分数监听：true为启用，false为关闭")
    var listener: Boolean by value(false)
    @ValueDescription("监听时间间隔（单位：分钟）")
    var listenInterval: Int by value(10)
    @ValueDescription("地图轮换提醒：true为启用，false为关闭")
    var mapRotationReminder: Boolean by value(false)
    @ValueDescription("字体")
    var font: String by value("微软雅黑")
    @ValueDescription("是否使用代理：true为启用，false为关闭")
    var ifProxy: Boolean by value(false)
    @ValueDescription("代理类型：HTTP、SOCKS")
    var proxyType: String by value("HTTP")
    @ValueDescription("代理地址")
    var hostName: String by value("127.0.0.1")
    @ValueDescription("代理端口")
    var port: Int by value(7890)
}