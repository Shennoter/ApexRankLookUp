package pers.shennoter

import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

import net.mamoe.mirai.contact.Contact.Companion.sendImage
import java.io.File
import java.net.URL


object RankLookUp : KotlinPlugin(
    JvmPluginDescription(
        id = "pers.shennoter.RankLookUp",
        name = "RankLookUp",
        version = "0.2.0",
    )
){
    override fun onEnable() {
        logger.info("apex查询插件已载入")
        CommandManager.registerCommand(Player)
        CommandManager.registerCommand(Map)
        CommandManager.registerCommand(Craft)
    }
}

object Player : SimpleCommand(
    RankLookUp, "apexid","玩家查询" ,
    description = "查询玩家信息"
) {
    @Handler
    suspend fun CommandSender.apexPlayerInfo(id: String) {
        val info = playerStat(id)
        subject?.sendMessage(info)

    }
}

object Map : SimpleCommand(
    RankLookUp, "apexmap","地图查询" ,
    description = "查询地图轮换"
){
    @Handler
    suspend fun CommandSender.apexMapInfo() {
        val info = mapStat()
        subject?.sendMessage(info)
    }
}

object Craft : SimpleCommand(
    RankLookUp, "apexcraft","复制器查询" ,
    description = "查询复制器轮换"
){
    @Handler
    suspend fun CommandSender.apexCraftInfo() {
        val info = craftStat()
        try {
            subject?.sendImage(File("./data/pers.shennoter.ranklookup/craft.png"))
        }catch (e:Exception){
            RankLookUp.logger.error("图片出错")
        }
    }
}
