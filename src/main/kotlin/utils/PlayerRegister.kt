package utils

import bean.Users
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.message.data.messageChainOf
import net.mamoe.mirai.message.nextMessage
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import pers.shennoter.ApexImage
import pers.shennoter.Config
import pers.shennoter.RankLookUp
import playerStat
import java.io.File

//使用事件通道监听查询自己信息的事件，只有这样才能获取到sender的qq号
fun playerRegister() {
    GlobalEventChannel.parentScope(GlobalScope).subscribeAlways<GroupMessageEvent> { event ->
        if (event.message.content == "/apex") {
            val gson = Gson()
            val users = gson.fromJson(File("${RankLookUp.dataFolder}/Users.json").readText(), Users::class.java)
            val qid = sender.id
            if (qid in users.data.keys) {
                users.data[qid]?.forEach {
                    Thread.sleep(2000)
                    val image = ApexImage()
                    val code = playerStat(it, image)
                    when (Config.mode) {
                        "pic" -> {
                            if (code == "查询成功") {
                                subject.sendMessage(messageChainOf(At(qid), image.get().uploadAsImage(subject)))
                                RankLookUp.logger.info(code)
                            } else {
                                RankLookUp.logger.error(code)
                                subject.sendMessage(code!!)
                            }
                        }
                        "text" -> {
                            subject.sendMessage(messageChainOf(At(qid), PlainText(code!!)))
                        }
                        else -> subject.sendMessage("config.yml回复模式配置错误，请检查")
                    }
                }
            } else {
                subject.sendMessage(message.quote() + "未绑定，请发送橘子ID")
                val nextMsg = nextMessage()
                val id = nextMsg.content
                var url =
                    "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=${id}&auth=${Config.apiKey}"
                var res = getRes(url)
                if (res.first == 1) {
                    if (Config.extendApiKey.isNotEmpty()) { //如果api过热且config有额外apikey，则使用额外apikey重试
                        run breaking@{
                            Config.extendApiKey.forEach {
                                url =
                                    "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=${id}&auth=$it"
                                res = getRes(url)
                                if (res.first == 0) return@breaking
                            }
                        }
                    }
                }
                if (res.first == 1) { //如果还是不行就报错
                    subject.sendMessage(res.second!!)
                    RankLookUp.logger.error(res.second)
                }
                if (res.first == 0) {
                    users.data[sender.id] = mutableListOf(id)
                    File("${RankLookUp.dataFolder}/Users.json").writeText(gson.toJson(users))
                    subject.sendMessage("绑定成功")
                }
            }
        }
    }
}

fun playerBonder() {
    GlobalEventChannel.parentScope(GlobalScope).subscribeAlways<GroupMessageEvent> { event ->
        if (event.message.content.contains("/apexbd")) {
            var id = event.message.content.substringAfter(" ")
            val gson = Gson()
            val users = gson.fromJson(File("${RankLookUp.dataFolder}/Users.json").readText(), Users::class.java)
            val qid = sender.id
            if ((qid in users.data.keys) && (users.data[qid]?.contains(id) == true)) {
                subject.sendMessage(message.quote() + "该ID已绑定")
            } else {
                var url =
                    "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=${id}&auth=${Config.apiKey}"
                var res = getRes(url)
                if (res.first == 1) {
                    if (Config.extendApiKey.isNotEmpty()) { //如果api过热且config有额外apikey，则使用额外apikey重试
                        run breaking@{
                            Config.extendApiKey.forEach {
                                url =
                                    "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=${id}&auth=$it"
                                res = getRes(url)
                                if (res.first == 0) return@breaking
                            }
                        }
                    }
                }
                if (res.first == 1) { //如果还是不行就报错
                    subject.sendMessage(res.second!!)
                    RankLookUp.logger.error(res.second)
                }
                if (res.first == 0) {
                    users.data[qid]?.add(id)
                    File("${RankLookUp.dataFolder}/Users.json").writeText(gson.toJson(users))
                    subject.sendMessage("绑定成功")
                }
            }
        }
    }
}

fun playerUnbonder(){
    GlobalEventChannel.parentScope(GlobalScope).subscribeAlways<GroupMessageEvent> { event ->
        if (event.message.content.contains("/apexubd")) {
            var id = event.message.content.substringAfter(" ")
            val gson = Gson()
            val users = gson.fromJson(File("${RankLookUp.dataFolder}/Users.json").readText(), Users::class.java)
            val qid = sender.id
            if ((qid in users.data.keys) && (users.data[qid]?.contains(id) == true)) {
                users.data[qid]?.remove(id)
                File("${RankLookUp.dataFolder}/Users.json").writeText(gson.toJson(users))
                subject.sendMessage("解绑成功")
            } else {
                subject.sendMessage("ID未绑定")
            }
        }
    }
}