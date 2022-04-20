package pers.shennoter

import craftStat
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.command.BuiltInCommands.AutoLoginCommand.add
import net.mamoe.mirai.console.command.BuiltInCommands.AutoLoginCommand.clear
import net.mamoe.mirai.console.command.BuiltInCommands.AutoLoginCommand.setConfig
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

import net.mamoe.mirai.contact.Contact.Companion.sendImage
import pers.shennoter.RankLookUp.reload
import playerStat
import java.io.File

object RankLookUp : KotlinPlugin(
    JvmPluginDescription(
        id = "pers.shennoter.RankLookUp",
        name = "RankLookUp",
        version = "1.1.0",
    )
){
    override fun onEnable() {
        logger.info("Apex查询插件已启动")
        Config.reload()
        CommandManager.registerCommand(Player)
        CommandManager.registerCommand(Map)
        CommandManager.registerCommand(Craft)
        logger.info("Apex查询插件已载入")
        val folder1 = File("./data/pers.shennoter.ranklookup/")
        if(!folder1.exists()) {
            folder1.mkdirs()
        }
        if (Config.ApiKey == ""){
            logger.error("未找到ApiKey，请到 https://apexlegendsapi.com/ 获取ApiKey填入 ./config/pers.shennoter.ranklookup/config.yml 中并重启mirai-console")
        }
    }
}

object Player : SimpleCommand(
    RankLookUp, "apexid","玩家查询" ,
    description = "查询玩家信息"
) {
    @Handler
    suspend fun CommandSender.apexPlayerInfo(id: String) {
        val code = playerStat(id)
        when(Config.mode){
            "pic"-> {
                if (code == "查询成功") {
                    try {
                        subject?.sendImage(File("./data/pers.shennoter.ranklookup/player.png"))
                    } catch (e: Exception) {
                        RankLookUp.logger.error("图片读取出错")
                    }
                    RankLookUp.logger.info(code)
                } else {
                    RankLookUp.logger.error(code)
                    subject?.sendMessage(code)
                }
            }
            "text"->{
                subject?.sendMessage(code)
            }
            else -> subject?.sendMessage("config.yml配置错误，请检查")
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
        when(Config.mode){
            "pic"-> {
                if (code == "查询成功") {
                    try {
                        subject?.sendImage(File("./data/pers.shennoter.ranklookup/map.png"))
                    } catch (e: Exception) {
                        RankLookUp.logger.error("图片读取出错")
                    }
                    RankLookUp.logger.info(code)
                } else {
                    RankLookUp.logger.error(code)
                    subject?.sendMessage(code)
                }
            }
            "text"->{
                subject?.sendMessage(code)
            }
            else -> subject?.sendMessage("config.yml配置错误，请检查")
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
            RankLookUp.logger.info(code)
        }
        else {
            RankLookUp.logger.error(code)
            subject?.sendMessage(code)
        }
    }
}