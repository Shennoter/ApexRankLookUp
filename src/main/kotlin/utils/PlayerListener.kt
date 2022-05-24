package pers.shennoter

import ApexResponsePlayer
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import pers.shennoter.RankLookUp.dataFolder
import playerPicturMode
import playerTextMode
import java.io.File
import java.net.URL
import java.util.*

suspend fun playerStatListener(): Job {
    val playerJob = GlobalScope.launch {
        val listendPlayer: ListendPlayer =
            Gson().fromJson(File("$dataFolder/Data.json").readText(), ListendPlayer::class.java)
        listendPlayer.data.forEach { it_id ->
            if (it_id.key == "0") return@forEach
            delay(1500)
            val url =
                "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=${it_id.key}&auth=${Config.ApiKey}"
            var requestStr = URL(url).readText()
            var firstRes = Gson().fromJson(requestStr, ApexResponsePlayer::class.java)
            val cache = File("$dataFolder/score/listened_${it_id.key}.score")
            if (!cache.exists()) {
                cache.createNewFile()
            }
            cache.writeText(firstRes.global.rank.rankScore)
            it_id.value.forEach { it_group ->
                val bot = Bot.getInstanceOrNull(it_group!!)
                RankLookUp.logger.info("开始监听${it_id.key}:$it_group")
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        GlobalScope.launch {
                            val res = Gson().fromJson(URL(url).readText(), ApexResponsePlayer::class.java)
                            if (res.global.rank.rankScore != cache.readText()) {
                                if (Config.mode == "pic") {
                                    cache.writeText(res.global.rank.rankScore)
                                    val image = ApexImage()
                                    playerPicturMode(res, it_id.key, image)
                                    if (bot != null) {
                                        Objects.requireNonNull(bot.getGroup(it_group))
                                            ?.sendMessage("${it_id.key}的分数已更新！")
                                        Objects.requireNonNull(bot.getGroup(it_group))?.sendImage(image.get())
                                    }
                                } else {
                                    cache.writeText(res.global.rank.rankScore)
                                    if (bot != null) {
                                        Objects.requireNonNull(bot.getGroup(it_group))
                                            ?.sendMessage("${it_id.key}的分数已更新！")
                                        Objects.requireNonNull(bot.getGroup(it_group))
                                            ?.sendMessage(playerTextMode(res, it_id.key))
                                    }
                                }
                            }
                        }
                        RankLookUp.logger.info("完成一次对${it_id.key}:${it_group}的监听")
                    }
                }, Config.listenInterval.toLong() * 60 * 1000, Config.listenInterval.toLong() * 60 * 1000)
            }
        }
    }
    return playerJob
}
