package utils

import ApexLookUp

import com.google.gson.Gson
import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import ApexLookUp.dataFolder
import ApexLookUp.logger
import bean.ApexResponsePlayer
import config.Config
import bean.ListendPlayer
import functions.playerPicturMode
import functions.playerTextMode

import java.io.File
import java.util.*


var playerTask: TimerTask? = null
var playerTimer: Timer? = Timer()

@OptIn(DelicateCoroutinesApi::class)
suspend fun playerStatListener(){
    //取消上一个Timer和TimerTask
    playerTask?.cancel()
    playerTask = null
    playerTimer?.cancel()
    playerTimer?.purge()
    playerTimer = null
    val listendPlayer: ListendPlayer = Gson().fromJson(File("$dataFolder/Data.json").readText(), ListendPlayer::class.java)
    listendPlayer.data.forEach { //遍历玩家id创建分数缓存
        if (it.key == "0") return@forEach //跳过占位符
        delay(2000) //延迟2秒防止api过热
        var url = "https://api.mozambiquehe.re/bridge?version=5&platform=${Config.platform}&player=${it.key}&auth=${Config.apiKey}"
        var requestStr = getRes(url)
        if (requestStr.first == 1) {
            if(Config.extendApiKey.isNotEmpty()){ //如果api过热且config有额外apikey，则使用额外apikey重试
                run breaking@{
                    Config.extendApiKey.forEach { ex_api ->
                        url = "https://api.mozambiquehe.re/bridge?version=5&platform=${Config.platform}&player=${it.key}&auth=$ex_api"
                        requestStr = getRes(url)
                        if (requestStr.first == 0) return@breaking
                    }
                }
            }
        }
        if (requestStr.first == 1) { // 检查其他平台
            run breaking@{
                listOf("PC", "X1", "PS4", "SWITCH").forEach { platform ->
                    Thread.sleep(2000)
                    url =
                        "https://api.mozambiquehe.re/bridge?version=5&platform=${platform}&player=${it.key}&auth=${Config.apiKey}"
                    requestStr = getRes(url)
                    if (requestStr.first == 0) return@breaking
                }
            }
        }
        val firstRes = try {
            Gson().fromJson(requestStr.second, ApexResponsePlayer::class.java)
        }catch (e:Exception){
            ApexLookUp.logger.error("数据获取失败:${e.message}") //非null检查
            return@forEach
        }
        val cache = File("$dataFolder/score/listened_${it.key}.score") //缓存文件，保存玩家分数
        if (!cache.exists()) {
            cache.createNewFile()
        }
        cache.writeText(firstRes.global.rank.rankScore) //将首次获取到的分数写入缓存文件
    }
    playerTask = object :TimerTask() { //定时任务
        override fun run() {
            listendPlayer.data.forEach { it_id -> //遍历玩家id
                if (it_id.key == "0") return@forEach //跳过占位符
                Thread.sleep(2000) //延迟2秒防止api过热
                GlobalScope.launch { //每个id开一个协程
                    var url = "https://api.mozambiquehe.re/bridge?version=5&platform=${Config.platform}&player=${it_id.key}&auth=${Config.apiKey}"
                    val cache = File("$dataFolder/score/listened_${it_id.key}.score")
                    var requestStr = getRes(url)
                    if (requestStr.first == 1) {
                        if(Config.extendApiKey.isNotEmpty()){ //如果api过热且config有额外apikey，则使用额外apikey重试
                            run breaking@{
                                Config.extendApiKey.forEach { ex_api ->
                                    url = "https://api.mozambiquehe.re/bridge?version=5&platform=${Config.platform}&player=${it_id.key}&auth=$ex_api"
                                    requestStr = getRes(url)
                                    if (requestStr.first == 0) return@breaking
                                }
                            }
                        }
                    }
                    if (requestStr.first == 1) { // 检查其他平台
                        run breaking@{
                            listOf("PC", "X1", "PS4", "SWITCH").forEach { platform ->
                                Thread.sleep(2000)
                                url =
                                    "https://api.mozambiquehe.re/bridge?version=5&platform=${platform}&player=${it_id.key}&auth=${Config.apiKey}"
                                requestStr = getRes(url)
                                if (requestStr.first == 0) return@breaking
                            }
                        }
                    }
                    if(requestStr.first == 1){
                        logger.error("本次对${it_id.key}监听错误，原因：${requestStr.second}，不用报告本次错误")
                        this.cancel()
                    }
                    val res = Gson().fromJson(requestStr.second, ApexResponsePlayer::class.java)
                    if (res.global.rank.rankScore != cache.readText()) { //如果分数变化
                        if (Config.mode == "pic") { //图片模式
                            val image = ApexImage()
                            playerPicturMode(res, it_id.key, image)
                            it_id.value.forEach { it_group -> //遍历玩家id对应的群号
                                Bot.instances.forEach { //遍历bot实例发送消息
                                    it.getGroup(it_group!!)
                                        ?.sendMessage("${it_id.key}的分数已更新!\n${cache.readText()} --> ${res.global.rank.rankScore}")
                                    if(Config.listenerInfoType) {
                                        it.getGroup(it_group)?.sendImage(image.get())
                                    }
                                }
                            }
                            cache.writeText(res.global.rank.rankScore)
                        } else { //文字模式
                            it_id.value.forEach { it_group -> //遍历玩家id对应的群号
                                Bot.instances.forEach { //遍历bot实例发送消息
                                    it.getGroup(it_group!!)
                                        ?.sendMessage("${it_id.key}的分数已更新!\n${cache.readText()} --> ${res.global.rank.rankScore}")
                                    if(Config.listenerInfoType) {
                                        it.getGroup(it_group)?.sendMessage(playerTextMode(res, it_id.key))
                                    }
                                }
                            }
                            cache.writeText(res.global.rank.rankScore)  //将刚获取到的分数写入缓存文件
                        }
                    }
                    logger.info("完成了一次对${it_id}的监听")
                }
            }
        }
    }
    playerTimer = Timer()
    playerTimer!!.schedule(playerTask, 0, Config.listenInterval.toLong() * 60 * 1000) //开始执行定时任务
}