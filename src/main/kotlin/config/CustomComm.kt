package config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value


object CustomComm : AutoSavePluginConfig("CustomCommand") {
    @ValueDescription("所有指令都只能自定义一条，不能有空格等特殊符号\n可自定义包括/的内容")
    val apex: String by value("查询")
    val apexbd: String by value("绑定帐号")
    val apexubd: String by value("解绑帐号")
}