package pers.shennoter


import Cache
import Craft
import Help
import LeaderBoard
import Listener
import ListenerRemove
import News
import Player
import Predator
import Map
import bean.Users
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.nextEvent
import net.mamoe.mirai.event.selectMessages
import net.mamoe.mirai.event.whileSelectMessages
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.nextMessage
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import playerStat
import utils.*
import java.io.File
import java.io.InputStream
import java.util.Timer
import java.util.TimerTask

var mapTask: TimerTask? = null
var playerTask: TimerTask? = null
object RankLookUp : KotlinPlugin(
    JvmPluginDescription(
        id = "pers.shennoter.RankLookUp",
        name = "ApexLookUp",
        version = "1.5.1",
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
        CommandManager.registerCommand(Help)
        CommandManager.registerCommand(LeaderBoard)
        logger.info("众神之父赐予我视野！")
        //清理缓存
        if(Config.cacheAutoDel){
            RankLookUp.logger.info(removeCache(true))
        }
        createFiles()
        startListening()
        playerRegister()
        playerBonder()
        playerUnbonder()
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
        CommandManager.unregisterCommand(Help)
        CommandManager.unregisterCommand(LeaderBoard)
        logger.info("我是布洛特·亨德尔，你可以叫我倒地了但还活着！")
    }
}

fun startListening(){
    if(Config.apiKey == ""){
        RankLookUp.logger.error("未找到ApiKey，请到https://apexlegendsapi.com/获取ApiKey填入./config/pers.shennoter.RankLookUp/config.yml中并重启mirai-console")
    }
    else{
        GlobalScope.launch { //启动监听任务
            val listendPlayer : ListendPlayer = Gson().fromJson(File("${RankLookUp.dataFolder}/Data.json").readText(), ListendPlayer::class.java)
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



