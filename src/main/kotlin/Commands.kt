import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import pers.shennoter.*

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
        var result = removeFileByTime(false)
        RankLookUp.logger.info(result)
        subject?.sendMessage(result)
    }
}