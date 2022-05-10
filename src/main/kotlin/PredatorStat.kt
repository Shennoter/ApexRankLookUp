import com.google.gson.Gson
import pers.shennoter.*
import java.awt.Color
import java.awt.image.BufferedImage
import java.net.URL

fun predatorStat(image: ApexImage):String{
    if(Config.ApiKey == "") {
        return "未填写ApiKey"
    }
    var requestStr = ""
    var code = "查询成功"
    try {
        val url = "https://api.mozambiquehe.re/predator?auth=${Config.ApiKey}"
        requestStr = URL(url).readText()
    }catch (e:Exception){
        code = "错误，短时间内请求过多,请稍后再试"
        RankLookUp.logger.error(code)
        return code
    }
    val res = Gson().fromJson(requestStr, ApexResponsePredator::class.java)
    if (Config.mode == "pic"){
        predatorPictureMode(res, image)
    }
    else{
        code = predatorTextMode(res)
    }
    return code
}

fun predatorPictureMode(res:ApexResponsePredator, image:ApexImage){
    val background: BufferedImage = ImageCache("predatorbg","https://shennoter.top/wp-content/uploads/mirai/predator.png")
    var img = drawTextToImage(background,res.RP.PC.foundRank.toString(),520,1320,70, Color.white)
    img = drawTextToImage(img,res.RP.PC.`val`.toString(),520,1440,70, Color.white)
    img = drawTextToImage(img,res.RP.PC.totalMastersAndPreds.toString(),520,1555,70, Color.white)
    img = drawTextToImage(img,res.AP.PC.foundRank.toString(),1460,1320,70, Color.white)
    img = drawTextToImage(img,res.AP.PC.`val`.toString(),1460,1440,70, Color.white)
    img = drawTextToImage(img,res.AP.PC.totalMastersAndPreds.toString(),1460,1555,70, Color.white)
    img = drawTextToImage(img,"#" + res.RP.PC.foundRank.toString(),480,1130,40, Color.white)
    img = drawTextToImage(img,"#" + res.AP.PC.foundRank.toString(),1420,1130,40, Color.white)
    image.save(img)
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