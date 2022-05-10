import com.google.gson.Gson
import pers.shennoter.*
import java.awt.image.BufferedImage
import java.net.URL


fun craftStat(image: ApexImage):String{
    if(Config.ApiKey == "") {
        return "未填写ApiKey"
    }
    var requestStr = ""
    var code = "查询成功"
    try {
        val url = "https://api.mozambiquehe.re/crafting?&auth=${Config.ApiKey}"
        requestStr = URL(url).readText()
    }catch (e:Exception){
        code = "错误，短时间内请求过多,请稍后再试"
        RankLookUp.logger.error(code)
        return code
    }
    val res = Gson().fromJson(requestStr, ApexResponseCraft::class.java)
    val daily1: BufferedImage = ImageCache("craft_"+res[0].bundleContent[0].itemType.name,res[0].bundleContent[0].itemType.asset)
    val daily2: BufferedImage = ImageCache("craft_"+res[0].bundleContent[1].itemType.name,res[0].bundleContent[1].itemType.asset)
    val weekly1: BufferedImage = ImageCache("craft_"+res[1].bundleContent[0].itemType.name,res[1].bundleContent[0].itemType.asset)
    val weekly2: BufferedImage = ImageCache("craft_"+res[1].bundleContent[1].itemType.name,res[1].bundleContent[1].itemType.asset)
    val img1: BufferedImage = mergeImage(true, daily1, daily2)
    val img2: BufferedImage = mergeImage(true, weekly1, weekly2)
    val img = mergeImage(false, img1, img2)
    image.save(img)
    return code
}