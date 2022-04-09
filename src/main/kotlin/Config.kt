package pers.shennoter

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object Config : AutoSavePluginConfig("config") {
    @ValueDescription("ApiKey,如果没有请到 https://apexlegendsapi.com/ 获取")
    val ApiKey: String by value("")
    @ValueDescription("回复模式: pic为图片，text为文字(只适用于玩家信息和地图轮换)")
    var mode: String by value("pic")
}