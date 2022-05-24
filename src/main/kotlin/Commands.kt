import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import pers.shennoter.*
import pers.shennoter.RankLookUp.dataFolder
import pers.shennoter.RankLookUp.logger
import java.io.File
import java.net.URL
import java.util.*

object Player : SimpleCommand(
    RankLookUp, "apexid","玩家查询" ,
    description = "查询玩家信息"
) {
    @Handler
    suspend fun CommandSender.apexPlayerInfo(id: String) {
        val image = ApexImage()
        val code = playerStat(id,image)
        when(Config.mode){
            "pic"-> {
                if (code == "查询成功") {
                    try {
                        subject?.sendImage(image.get())
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
        val image = ApexImage()
        val code = mapStat(image)
        when(Config.mode){
            "pic"-> {
                if (code == "查询成功") {
                    try {
                        subject?.sendImage(image.get())
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
        val image = ApexImage()
        val code = craftStat(image)
        if (code == "查询成功") {
            try {
                subject?.sendImage(image.get())
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

object Predator : SimpleCommand(
    RankLookUp, "apexpred","猎杀查询" ,
    description = "查询最低猎杀信息"
) {
    @Handler
    suspend fun CommandSender.apexPredatorInfo() {
        val image = ApexImage()
        val code = predatorStat(image)
        when(Config.mode){
            "pic"-> {
                if (code == "查询成功") {
                    try {
                        subject?.sendImage(image.get())
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

object News : SimpleCommand(
    RankLookUp, "apexnews","新闻查询" ,
    description = "查询官方新闻活动"
) {
    @Handler
    suspend fun CommandSender.apexNews(index:Int) {
        val image = ApexImage()
        val code = newsStat(image,index)
        if(code == "序号无效") {
            RankLookUp.logger.error(code)
            subject?.sendMessage(code)
        }
        else {
            try {
                subject?.sendImage(image.get())
                subject?.sendMessage(code)
            } catch (e: Exception) {
                RankLookUp.logger.error("查询出错")
                subject?.sendMessage("查询出错")
            }
        }
    }
}

object Cache : SimpleCommand(
    RankLookUp, "apexcache","清除缓存" ,
    description = "清除产生的缓存文件，解决可能出现的缓存问题"
) {
    @Handler
    suspend fun CommandSender.apexDelCache() {
        val result = removeFileByTime(false)
        RankLookUp.logger.info(result)
        subject?.sendMessage(result)
    }
}

object Listener : CompositeCommand(
    RankLookUp, "apexadd", description = "添加监听",
) {
    @SubCommand
    suspend fun CommandSender.id(id: String) {
        val url = "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=$id&auth=${Config.ApiKey}"
        try {
            URL(url).readText()
        }
        catch(e:Exception){
            subject?.sendMessage("玩家ID不存在")
            logger.error("玩家ID不存在")
        }
        val gson = Gson()
        val listendPlayer : ListendPlayer = gson.fromJson(File("$dataFolder/Data.json").readText(), ListendPlayer::class.java)
        if(listendPlayer.data.contains(id)) {
            if(listendPlayer.data[id]?.contains(subject?.id) == true) {
                subject?.sendMessage("该玩家已在监听列表")
                return
            }
            else {
                listendPlayer.data[id]?.add(subject?.id)
            }
        }
        else {
            listendPlayer.data[id] = arrayListOf(subject?.id)
        }
        File("$dataFolder/Data.json").writeText(gson.toJson(listendPlayer))
        logger.info("添加对${id}:${subject?.id}的监听成功")
        subject?.sendMessage("添加对${id}:${subject?.id}的监听成功")
        playerJob?.cancel()
        playerJob = playerStatListener()
    }

    @SubCommand
    suspend fun CommandSender.map() {
        val gson = Gson()
        val groups : GroupReminding = gson.fromJson(File("${RankLookUp.dataFolder}/Reminder.json").readText(), GroupReminding::class.java)
        if(groups.data.contains(subject?.id)){
            subject?.sendMessage("该群已在提醒列表")
            logger.error("该群已在提醒列表")
            return
        }else {
            groups.data.add(subject?.id)
        }
        File("$dataFolder/Reminder.json").writeText(gson.toJson(groups))
        logger.info("已添加对${subject?.id}的地图轮换提醒")
        subject?.sendMessage("已添加对${subject?.id}的地图轮换提醒")
        mapTask?.cancel()
        mapTask = mapReminder()
    }
}

object ListenerRemove : CompositeCommand(
    RankLookUp, "apexremove","移除id" ,
    description = "取消对某玩家的监听"
) {
    @SubCommand
    suspend fun CommandSender.id(id: String) {
        val gson = Gson()
        val listendPlayer : ListendPlayer = gson.fromJson(File("$dataFolder/Data.json").readText(), ListendPlayer::class.java)
        if(listendPlayer.data.contains(id) && listendPlayer.data[id]?.contains(subject?.id) == true) {
            listendPlayer.data[id]?.remove(subject?.id)
            subject?.sendMessage("已取消对${id}:${subject?.id}的监听")
            logger.info("已取消对${id}:${subject?.id}的监听")
            playerJob?.cancel()
            playerJob = playerStatListener()
        }
        else {
            subject?.sendMessage("${id}:${subject?.id}不在监听列表")
            logger.error("${id}:${subject?.id}不在监听列表")
        }
        File("$dataFolder/Data.json").writeText(gson.toJson(listendPlayer))
    }

    @SubCommand
    suspend fun CommandSender.map(){
        val gson = Gson()
        val groups : GroupReminding = gson.fromJson(File("${RankLookUp.dataFolder}/Reminder.json").readText(), GroupReminding::class.java)
        if(groups.data.contains(subject?.id)){
            groups.data.remove(subject?.id)
            subject?.sendMessage("已取消该群提醒")
            logger.error("已取消该群提醒")
            mapTask?.cancel()
            mapTask = mapReminder()
        }
        else{
            subject?.sendMessage("该群不在提醒列表")
            logger.error("该群不在提醒列表")
        }
    }
}