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
                val image = ApexImage()
                val code = playerStat(users.data[qid]!!, image)
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
            } else {
                subject.sendMessage(message.quote() + "未绑定，请发送橘子ID")
                val nextMsg = nextMessage()
                var url =
                    "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=${nextMsg.content}&auth=${Config.apiKey}"
                var res = getRes(url)
                if (res.first == 1) {
                    if (Config.extendApiKey.isNotEmpty()) { //如果api过热且config有额外apikey，则使用额外apikey重试
                        run breaking@{
                            Config.extendApiKey.forEach {
                                url =
                                    "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=${nextMsg.content}&auth=$it"
                                res = getRes(url)
                                if (res.first == 0) return@breaking
                            }
                        }
                    }
                }
                if (res.first == 1) { //如果还是不行就报错返回
                    subject.sendMessage(res.second!!)
                    RankLookUp.logger.error(res.second)
                    return@subscribeAlways
                }
                users.data[sender.id] = nextMsg.content
                File("${RankLookUp.dataFolder}/Users.json").writeText(gson.toJson(users))
                subject.sendMessage("绑定成功")
            }
        }
    }
}