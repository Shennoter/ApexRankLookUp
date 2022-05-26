import com.google.gson.Gson

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import pers.shennoter.*
import pers.shennoter.RankLookUp.dataFolder
import pers.shennoter.RankLookUp.logger
import utils.getRes
import java.io.File
import java.net.URL

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
                    subject?.sendMessage(code!!)
                }
            }
            "text"->{
                subject?.sendMessage(code!!)
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
                    subject?.sendMessage(code!!)
                }
            }
            "text"->{
                subject?.sendMessage(code!!)
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
            subject?.sendMessage(code!!)
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
                    subject?.sendMessage(code!!)
                }
            }
            "text"->{
                subject?.sendMessage(code!!)
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
                subject?.sendMessage(code!!)
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
        val result = removeCache(true)
        RankLookUp.logger.info(result)
        subject?.sendMessage(result)
    }
}

object Listener : CompositeCommand(
    RankLookUp, "apexadd",
    description = "添加监听",
) {
    @SubCommand
    suspend fun CommandSender.id(id: String) {
        var url = "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=$id&auth=${Config.apiKey}"
        var res = getRes(url)
        if (res.first == 1) {
            if(Config.extendApiKey.isNotEmpty()){ //如果api过热且config有额外apikey，则使用额外apikey重试
                run breaking@{
                    Config.extendApiKey.forEach {
                        url = "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=$id&auth=$it"
                        res = getRes(url)
                        if (res.first == 0) return@breaking
                    }
                }
            }
        }
        if(res.first == 1){ //如果还是不行就报错返回
            subject?.sendMessage(res.second!!)
            logger.error(res.second)
            return
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
        subject?.sendMessage("监听添加成功：\nOrigin ID：${id}\n群号：${subject?.id}")
        if(Config.listener) {
            logger.info("重启监听线程")
            playerTask?.cancel()
            playerTask = playerStatListener()
        }
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
        logger.info("已添加对[${subject?.id}]的地图轮换提醒")
        subject?.sendMessage("已添加对[${subject?.id}]的地图轮换提醒")
        if(Config.mapRotationReminder) {
            logger.info("重启监听线程")
            mapTask?.cancel()
            mapTask = mapReminder()
        }
    }
}

object ListenerRemove : CompositeCommand(
    RankLookUp, "apexremove",
    description = "取消监听"
) {
    @SubCommand
    suspend fun CommandSender.id(id: String) {
        val gson = Gson()
        val listendPlayer : ListendPlayer = gson.fromJson(File("$dataFolder/Data.json").readText(), ListendPlayer::class.java)
        if(listendPlayer.data.contains(id) && listendPlayer.data[id]?.contains(subject?.id) == true) {
            listendPlayer.data[id]?.remove(subject?.id) //删除id对应群号
            if(listendPlayer.data[id].isNullOrEmpty()){ //若id对应的所有群号都被删除，则删除此id
                listendPlayer.data.remove(id)
            }
            File("$dataFolder/Data.json").writeText(gson.toJson(listendPlayer))
            subject?.sendMessage("已取消对${id}[${subject?.id}]的监听")
            logger.info("已取消对${id}[${subject?.id}]的监听")
            if(Config.listener) {
                logger.info("重启监听线程")
                playerTask?.cancel()
                playerTask = playerStatListener()
            }
        }
        else {
            subject?.sendMessage("${id}[${subject?.id}]不在监听列表")
            logger.error("${id}[${subject?.id}]不在监听列表")
        }

    }

    @SubCommand
    suspend fun CommandSender.map(){
        val gson = Gson()
        val groups : GroupReminding = gson.fromJson(File("${RankLookUp.dataFolder}/Reminder.json").readText(), GroupReminding::class.java)
        if(groups.data.contains(subject?.id)){
            groups.data.remove(subject?.id)
            File("$dataFolder/Reminder.json").writeText(gson.toJson(groups))
            subject?.sendMessage("已取消该群提醒")
            logger.info("已取消该群提醒")
            if(Config.mapRotationReminder) {
                logger.info("重启监听线程")
                mapTask?.cancel()
                mapTask = mapReminder()
            }
        }
        else{
            subject?.sendMessage("该群不在提醒列表")
            logger.error("该群不在提醒列表")
        }
    }
}