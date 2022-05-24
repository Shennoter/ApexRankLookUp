package pers.shennoter

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import java.io.File
import java.net.URL
import java.util.*


fun mapReminder() :TimerTask{
    val groups: GroupReminding =
        Gson().fromJson(File("${RankLookUp.dataFolder}/Reminder.json").readText(), GroupReminding::class.java)
    var requestStr = ""
    val url = "https://api.mozambiquehe.re/maprotation?version=2&auth=${Config.ApiKey}"
    try {
        requestStr = URL(url).readText()
    } catch (e: Exception) {
        RankLookUp.logger.error("地图信息获取错误，轮换监听已停止")
    }//获取第一次地图信息
    var res = Gson().fromJson(requestStr, ApexResponseMap::class.java)
    val cache = File("${RankLookUp.dataFolder}/map.time")
    if(!cache.exists()) {
        cache.createNewFile()
    }
    cache.writeText(res.battle_royale.current.end.toString())
    groups.data.forEach {
        if (it == 0.toLong()) return@forEach
        RankLookUp.logger.info("已启动对${it}的地图轮换提醒")
    }
    var firstStart = true
    val mapTask: TimerTask = object : TimerTask() {
        override fun run() {
            GlobalScope.launch {
                res = Gson().fromJson(
                    URL(url).readText(),
                    ApexResponseMap::class.java
                )//每半小时获取一次地图信息，通过比对map.time文件判断是否已在提醒计划中
                val storedEndTime = cache.readText().toLong()
                val endTime = res.battle_royale.current.end.toLong()
                val currentTime = System.currentTimeMillis() / 1000
                val timeToRemind = endTime - currentTime
                if (endTime != storedEndTime || firstStart) {
                    cache.writeText(endTime.toString())
                    GlobalScope.launch {
                        delay(timeToRemind)//计时到轮换时间进行提醒
                        groups.data.forEach {
                            if (it == 0.toLong()) return@forEach
                            val bot = Bot.getInstanceOrNull(it!!)
                            if (Config.mode == "pic") {
                                if (bot != null) {
                                    val image = ApexImage()
                                    mapPictureMode(res, image)
                                    Objects.requireNonNull(bot.getGroup(it))?.sendMessage("大逃杀地图已轮换")
                                    Objects.requireNonNull(bot.getGroup(it))?.sendImage(image.get())
                                }
                            } else {
                                if (bot != null) {
                                    Objects.requireNonNull(bot.getGroup(it))?.sendMessage("大逃杀地图已轮换")
                                    Objects.requireNonNull(bot.getGroup(it))?.sendMessage(mapTextMode(res))
                                }
                            }
                            RankLookUp.logger.info("完成一次对${it}的地图轮换提醒")
                        }
                    }
                }
            }
            firstStart = false
            RankLookUp.logger.info("完成一次地图监听")
        }
    }
    Timer().schedule(mapTask, 0, 30 * 60 * 1000)
    return mapTask
}