package pers.shennoter

import Cache
import Craft
import News
import Player
import Predator
import Map
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import java.io.File

object RankLookUp : KotlinPlugin(
    JvmPluginDescription(
        id = "pers.shennoter.RankLookUp",
        name = "RankLookUp",
        version = "1.3.0",
    )
){
    override fun onEnable() {
        logger.info("Apex查询插件已启动")
        Config.reload()
        CommandManager.registerCommand(Player)
        CommandManager.registerCommand(Map)
        CommandManager.registerCommand(Craft)
        CommandManager.registerCommand(Predator)
        CommandManager.registerCommand(News)
        CommandManager.registerCommand(Cache)
        logger.info("Apex查询插件已载入")
        val folder1 = File("./data/pers.shennoter.ranklookup/")
        if(!folder1.exists()) {
            folder1.mkdirs()
        }
        if(Config.ApiKey == ""){
            logger.error("未找到ApiKey，请到 https://apexlegendsapi.com/ 获取ApiKey填入 ./config/pers.shennoter.ranklookup/config.yml 中并重启mirai-console")
        }
        if(Config.cacheAutoDel == "true"){
            RankLookUp.logger.info(removeFileByTime(true))
        }
    }
}
