package pers.shennoter

import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

object RankLookUp : KotlinPlugin(
    JvmPluginDescription(
        id = "pers.shennoter.RankLookUp",
        name = "RankLookUp",
        version = "0.1.1",
    )
){
    override fun onEnable() {
        logger.info("战绩查询插件已载入")
        CommandManager.registerCommand(MyCommand)
    }
}

object MyCommand : CompositeCommand(
    RankLookUp, "rank","战绩查询" ,
    description = "查询Apex或R6战绩"
) {
    @SubCommand("apexid","apex玩家")
    suspend fun CommandSender.apexPlayerInfo(id: String) {
        val info = playerStat(id)
        subject?.sendMessage(info)
    }

    @SubCommand("apexmap", "地图轮换")
    suspend fun CommandSender.apexMapInfo() {
        val info = mapStat()
        subject?.sendMessage(info)
    }
}

