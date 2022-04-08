package pers.shennoter

import craftStat
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

import net.mamoe.mirai.contact.Contact.Companion.sendImage
import playerStat
import java.io.File


object RankLookUp : KotlinPlugin(
    JvmPluginDescription(
        id = "pers.shennoter.RankLookUp",
        name = "RankLookUp",
        version = "1.0.1",
    )
){
    override fun onEnable() {
        logger.info("apex查询插件已载入")
        val folder1 = File("./data/pers.shennoter.ranklookup/")
        if(!folder1.exists()) {
            folder1.mkdirs()
        }
        var folder2 = File("./config/pers.shennoter.ranklookup/")
        if(!folder2.exists()) {
            folder2.mkdirs()
        }

        var keyFile = File("./config/pers.shennoter.ranklookup/apikey.yml")
        if (!keyFile.exists()) {
            try {
                keyFile.createNewFile();
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }
        if (keyFile.length().toInt() == 0){
            logger.error("未找到ApiKey，请到 https://apexlegendsapi.com/ 获取ApiKey填入 ./config/pers.shennoter.ranklookup/apikey.yml 中并重启mirai-console")
        }
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
        val code = playerStat(id)
        if (code == "查询成功") {
            try {
                subject?.sendImage(File("./data/pers.shennoter.ranklookup/player.png"))
            } catch (e: Exception) {
                RankLookUp.logger.error("图片读取出错")
            }
            subject?.sendMessage(code)
        }
        else{
            subject?.sendMessage(code)
        }

    }
}

object Map : SimpleCommand(
    RankLookUp, "apexmap","地图查询" ,
    description = "查询地图轮换"
){
    @Handler
    suspend fun CommandSender.apexMapInfo() {
        val code = mapStat()
        if (code == "查询成功") {
            try {
                subject?.sendImage(File("./data/pers.shennoter.ranklookup/map.png"))
            } catch (e: Exception) {
                RankLookUp.logger.error("图片读取出错")
            }
            subject?.sendMessage(code)

        }
        else {
            subject?.sendMessage(code)
        }
    }
}

object Craft : SimpleCommand(
    RankLookUp, "apexcraft","复制器查询" ,
    description = "查询复制器轮换"
){
    @Handler
    suspend fun CommandSender.apexCraftInfo() {
        val code = craftStat()
        if (code == "查询成功") {
            try {
                subject?.sendImage(File("./data/pers.shennoter.ranklookup/craft.png"))
            } catch (e: Exception) {
                RankLookUp.logger.error("图片读取出错")
            }
            subject?.sendMessage(code)
        }
        else {
            subject?.sendMessage(code)
        }
    }
}