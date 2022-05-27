package pers.shennoter


import Cache
import Craft
import Listener
import ListenerRemove
import News
import Player
import Predator
import Map
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import java.io.File
import java.util.Timer
import java.util.TimerTask

var mapTask: TimerTask? = null
var playerTask: TimerTask? = null
object RankLookUp : KotlinPlugin(
    JvmPluginDescription(
        id = "pers.shennoter.RankLookUp",
        name = "ApexLookUp",
        version = "1.4.1",
    ){
        author("Shennoter")
    }
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
        CommandManager.registerCommand(Listener)
        CommandManager.registerCommand(ListenerRemove)
        logger.info("众神之父赐予我视野！")
        val folder1 = File("./data/pers.shennoter.RankLookUp/")
        if(!folder1.exists()) {
            folder1.mkdirs()
        }
        val folder2 = File("$dataFolder/score/")
        if(!folder2.exists()) {
            folder2.mkdirs()
        }
        val folder3 = File("$dataFolder/imgs/")
        if(!folder3.exists()) {
            folder3.mkdirs()
        }
        val listendID = File("$dataFolder/Data.json")
        if (!listendID.exists()) {
            File("$dataFolder/Data.json").writeText("{\"data\":{\"0\":[0]}}")
        }
        val remindingGroups = File("${RankLookUp.dataFolder}/Reminder.json")
        if (!remindingGroups.exists()) {
            File("$dataFolder/Reminder.json").writeText("{\"data\":[0]}")
        }

        if(Config.cacheAutoDel){
            RankLookUp.logger.info(removeCache(true))
        }
        if(Config.apiKey == ""){
            logger.error("未找到ApiKey，请到https://apexlegendsapi.com/获取ApiKey填入./config/pers.shennoter.RankLookUp/config.yml中并重启mirai-console")
        }
        else{
            GlobalScope.launch { //启动监听任务
                val listendPlayer : ListendPlayer = Gson().fromJson(File("$dataFolder/Data.json").readText(), ListendPlayer::class.java)
                if(Config.listener && listendPlayer.data.size > 1) {
                    playerTask = playerStatListener()
                }
                val groups : GroupReminding = Gson().fromJson(File("${RankLookUp.dataFolder}/Reminder.json").readText(), GroupReminding::class.java)
                if(Config.mapRotationReminder && groups.data.size > 1){
                    mapTask = mapReminder()
                }
            }
        }
    }

    override fun onDisable(){
        Config.save()
        CommandManager.unregisterCommand(Player)
        CommandManager.unregisterCommand(Map)
        CommandManager.unregisterCommand(Craft)
        CommandManager.unregisterCommand(Predator)
        CommandManager.unregisterCommand(News)
        CommandManager.unregisterCommand(Cache)
        CommandManager.unregisterCommand(Listener)
        CommandManager.unregisterCommand(ListenerRemove)
        logger.info("我是布洛特·亨德尔，你可以叫我倒地了但还活着！")
    }
}


