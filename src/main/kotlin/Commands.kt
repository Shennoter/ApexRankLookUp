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

object Player : SimpleCommand(
    RankLookUp, "apexid",
    description = "查询玩家信息"
) {
    @Handler
    suspend fun CommandSender.apexPlayerInfo(id: String, platform:String = Config.platform) {
        val image = ApexImage()
        val code = playerStat(id, image, platform)
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
    RankLookUp, "apexmap",
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
    RankLookUp, "apexcraft",
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
    RankLookUp, "apexpred",
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
    RankLookUp, "apexnews",
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
    RankLookUp, "apexcache",
    description = "清除产生的缓存文件，解决可能出现的缓存问题"
) {
    @Handler
    suspend fun CommandSender.apexDelCache() {
        val result = removeCache(true)
        RankLookUp.logger.info(result)
        subject?.sendMessage(result)
    }
}
object Help : SimpleCommand(
    RankLookUp, "apexhelp",
    description = "查看本插件所有指令"
) {
    @Handler
    suspend fun CommandSender.apexHelp() {
        var help = "+—ApexLookUp功能一览—+\n"
        help += "[ID]意为你的橘子ID，不要加[]\n"
        help += "/apex   查询已绑定的ID\n"
        help += "/apexbd [ID]  绑定ID\n"
        help += "/apexubd [ID]  解绑ID\n"
        help += "/apexubd all  解绑所有ID\n"
        help += "/apexid [ID]   查询玩家\n"
        help += "/apexmap   查询地图\n"
        help += "/apexcraft   查询复制器\n"
        help += "/apexpred   查询猎杀底分\n"
        help += "/apexnews [序号]   查询新闻\n"
        help += "/apexldb   排行榜链接\n"
        help += "/apexadd id [ID]   订阅玩家\n"
        help += "/apexrmv id [ID] 移除玩家订阅\n"
        help += "/apexadd map   订阅地图轮换\n"
        help += "/apexrmv map   移除地图订阅\n"
        help += "/apexadd info   本群已订阅ID\n"
        help += "+————————————+"
        subject?.sendMessage(help)
    }
}

object LeaderBoard : SimpleCommand(
    RankLookUp, "apexldb",
    description = "查看排行榜"
) {
    @Handler
    suspend fun CommandSender.apexLeaderBoard() {
        subject?.sendMessage("https://apexlegendsstatus.com/live-ranked-leaderboards/Battle_Royale/PC")
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
        if (res.first == 1) { // 检查其他平台
            run breaking@{
                listOf("PC", "X1", "PS4", "SWITCH").forEach { platform ->
                    Thread.sleep(2000)
                    url =
                        "https://api.mozambiquehe.re/bridge?version=5&platform=${platform}&player=${id}&auth=${Config.apiKey}"
                    res = getRes(url)
                    if (res.first == 0) return@breaking
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
        subject?.sendMessage("监听添加成功\nOrigin ID：${id}\n群号：${subject?.id}")
        if(Config.listener) {
            logger.info("重启监听线程")
            playerStatListener()
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
            mapReminder()
        }
    }

    @SubCommand
    suspend fun CommandSender.info() {
        val gson = Gson()
        val listendPlayer : ListendPlayer = gson.fromJson(File("$dataFolder/Data.json").readText(), ListendPlayer::class.java)
        var playerList = "本群已订阅的ID："
        listendPlayer.data.forEach{
            if(it.value.contains(subject?.id)){
                playerList += "\n" + it.key
            }
        }
        if(playerList == "本群已订阅的ID："){
            playerList += "本群无ID订阅"
        }
        subject?.sendMessage(playerList)
    }
}

object ListenerRemove : CompositeCommand(
    RankLookUp, "apexrmv",
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
                if(listendPlayer.data.size > 1) {
                    logger.info("重启监听线程")
                    playerStatListener()
                }
                else{
                    logger.info("监听列表为空，监听线程已关闭")
                }
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
                if(groups.data.size >1) {
                    logger.info("重启监听线程")
                    mapReminder()
                }else
                {
                    logger.info("提醒群为空，监听线程已关闭")
                }
            }
        }
        else{
            subject?.sendMessage("该群不在提醒列表")
            logger.error("该群不在提醒列表")
        }
    }
}