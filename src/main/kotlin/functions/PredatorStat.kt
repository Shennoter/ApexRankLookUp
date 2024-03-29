package functions

import bean.ApexResponsePredator
import com.google.gson.Gson
import config.Config
import utils.ApexImage
import utils.ImageCache
import utils.drawTextToImage
import utils.getRes
import java.awt.Color
import java.awt.image.BufferedImage


fun predatorStat(image: ApexImage):String?{
    if(Config.apiKey == "") {
        return "未填写ApiKey"
    }
    var url = "https://api.mozambiquehe.re/predator?auth=${Config.apiKey}"
    var requestStr = getRes(url)
    if (requestStr.first == 1) {
        if(Config.extendApiKey.isNotEmpty()){ //如果api过热且config有额外apikey，则使用额外apikey重试
            run breaking@{
                Config.extendApiKey.forEach {
                    url = "https://api.mozambiquehe.re/predator?auth=$it"
                    requestStr = getRes(url)
                    if (requestStr.first == 0) return@breaking //如果返回正确信息则跳出循环
                }
            }
        }
    }
    if (requestStr.first == 1) { //如果还是不行就报错返回
        return requestStr.second
    }
    val res = Gson().fromJson(requestStr.second, ApexResponsePredator::class.java)?: return "数据获取失败" //非null检查
    return if (Config.mode == "pic"){
        predatorPictureMode(res, image)
        "查询成功"
    }
    else{
        predatorTextMode(res)
    }
}

fun predatorPictureMode(res:ApexResponsePredator, image:ApexImage){
    val img = when(Config.platform){
        "PC" -> pc(res)
        "X1" -> x1(res)
        "PS4" -> ps(res)
        "SWITCH" -> switch(res)
        else -> {
            ApexLookUp.logger.error("默认平台配置错误")
            return
        }
    }
    image.save(img,false)
}

fun predatorTextMode(res:ApexResponsePredator):String{
    var predator = ""
    predator += "----------大逃杀------------" + "\n"
    predator += "最低排名：" + res.RP.PC.foundRank + "\n"
    predator += "排位分数:" + res.RP.PC.`val` + "\n"
    predator += "大师总数:" + res.RP.PC.totalMastersAndPreds + "\n"
    predator += "----------竞技场------------" + "\n"
    predator += "最低排名:" + res.AP.PC.foundRank + "\n"
    predator += "排位分数:" + res.AP.PC.`val` + "\n"
    predator += "大师总数:" + res.AP.PC.totalMastersAndPreds + "\n"
    return predator
}

//不知道怎么用变量来指向类属性，只能分成四个方法了T_T
fun pc(res:ApexResponsePredator):BufferedImage{
    val background: BufferedImage = ImageCache("predatorbg","https://shennoter.top/wp-content/uploads/mirai/predator.png")
    var img = drawTextToImage(background,res.RP.PC.foundRank.toString(),520,1320,70, Color.white)
    img = drawTextToImage(img,res.RP.PC.`val`.toString(),520,1440,70, Color.white)
    img = drawTextToImage(img,res.RP.PC.totalMastersAndPreds.toString(),520,1555,70, Color.white)
    img = drawTextToImage(img,res.AP.PC.foundRank.toString(),1460,1320,70, Color.white)
    img = drawTextToImage(img,res.AP.PC.`val`.toString(),1460,1440,70, Color.white)
    img = drawTextToImage(img,res.AP.PC.totalMastersAndPreds.toString(),1460,1555,70, Color.white)
    img = drawTextToImage(img,"#" + res.RP.PC.foundRank.toString(),480,1130,40, Color.white)
    img = drawTextToImage(img,"#" + res.AP.PC.foundRank.toString(),1420,1130,40, Color.white)
    return img
}

fun x1(res:ApexResponsePredator):BufferedImage{
    val background: BufferedImage = ImageCache("predatorbg","https://shennoter.top/wp-content/uploads/mirai/predator.png")
    var img = drawTextToImage(background,res.RP.X1.foundRank.toString(),520,1320,70, Color.white)
    img = drawTextToImage(img,res.RP.X1.`val`.toString(),520,1440,70, Color.white)
    img = drawTextToImage(img,res.RP.X1.totalMastersAndPreds.toString(),520,1555,70, Color.white)
    img = drawTextToImage(img,res.AP.X1.foundRank.toString(),1460,1320,70, Color.white)
    img = drawTextToImage(img,res.AP.X1.`val`.toString(),1460,1440,70, Color.white)
    img = drawTextToImage(img,res.AP.X1.totalMastersAndPreds.toString(),1460,1555,70, Color.white)
    img = drawTextToImage(img,"#" + res.RP.X1.foundRank.toString(),480,1130,40, Color.white)
    img = drawTextToImage(img,"#" + res.AP.X1.foundRank.toString(),1420,1130,40, Color.white)
    return img
}

fun ps(res:ApexResponsePredator):BufferedImage{
    val background: BufferedImage = ImageCache("predatorbg","https://shennoter.top/wp-content/uploads/mirai/predator.png")
    var img = drawTextToImage(background,res.RP.PS4.foundRank.toString(),520,1320,70, Color.white)
    img = drawTextToImage(img,res.RP.PS4.`val`.toString(),520,1440,70, Color.white)
    img = drawTextToImage(img,res.RP.PS4.totalMastersAndPreds.toString(),520,1555,70, Color.white)
    img = drawTextToImage(img,res.AP.PS4.foundRank.toString(),1460,1320,70, Color.white)
    img = drawTextToImage(img,res.AP.PS4.`val`.toString(),1460,1440,70, Color.white)
    img = drawTextToImage(img,res.AP.PS4.totalMastersAndPreds.toString(),1460,1555,70, Color.white)
    img = drawTextToImage(img,"#" + res.RP.PS4.foundRank.toString(),480,1130,40, Color.white)
    img = drawTextToImage(img,"#" + res.AP.PS4.foundRank.toString(),1420,1130,40, Color.white)
    return img
}

fun switch(res:ApexResponsePredator):BufferedImage{
    val background: BufferedImage = ImageCache("predatorbg","https://shennoter.top/wp-content/uploads/mirai/predator.png")
    var img = drawTextToImage(background,res.RP.SWITCH.foundRank.toString(),520,1320,70, Color.white)
    img = drawTextToImage(img,res.RP.SWITCH.`val`.toString(),520,1440,70, Color.white)
    img = drawTextToImage(img,res.RP.SWITCH.totalMastersAndPreds.toString(),520,1555,70, Color.white)
    img = drawTextToImage(img,res.AP.SWITCH.foundRank.toString(),1460,1320,70, Color.white)
    img = drawTextToImage(img,res.AP.SWITCH.`val`.toString(),1460,1440,70, Color.white)
    img = drawTextToImage(img,res.AP.SWITCH.totalMastersAndPreds.toString(),1460,1555,70, Color.white)
    img = drawTextToImage(img,"#" + res.RP.SWITCH.foundRank.toString(),480,1130,40, Color.white)
    img = drawTextToImage(img,"#" + res.AP.SWITCH.foundRank.toString(),1420,1130,40, Color.white)
    return img
}