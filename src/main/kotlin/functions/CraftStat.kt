import com.google.gson.Gson
import pers.shennoter.*
import utils.getRes
import java.awt.image.BufferedImage


fun craftStat(image: ApexImage):String?{
    if(Config.apiKey == "") {
        return "未填写ApiKey"
    }
    var url = "https://api.mozambiquehe.re/crafting?&auth=${Config.apiKey}"
    var requestStr = getRes(url)
    if (requestStr.first == 1) {
        if(Config.extendApiKey.isNotEmpty()){ //如果api过热且config有额外apikey，则使用额外apikey重试
            run breaking@{
                Config.extendApiKey.forEach {
                    url = "https://api.mozambiquehe.re/crafting?&auth=$it"
                    requestStr = getRes(url)
                    if (requestStr.first == 0) return@breaking //如果返回正确信息则跳出循环
                }
            }
        }
    }
    if (requestStr.first == 1) { //如果还是不行就报错返回
        return requestStr.second
    }
    val res = Gson().fromJson(requestStr.second, ApexResponseCraft::class.java)
    val daily1: BufferedImage = ImageCache("craft_"+res[0].bundleContent[0].itemType.name,res[0].bundleContent[0].itemType.asset)
    val daily2: BufferedImage = ImageCache("craft_"+res[0].bundleContent[1].itemType.name,res[0].bundleContent[1].itemType.asset)
    val weekly1: BufferedImage = ImageCache("craft_"+res[1].bundleContent[0].itemType.name,res[1].bundleContent[0].itemType.asset)
    val weekly2: BufferedImage = ImageCache("craft_"+res[1].bundleContent[1].itemType.name,res[1].bundleContent[1].itemType.asset)
    val img1: BufferedImage = mergeImage(true, daily1, daily2)
    val img2: BufferedImage = mergeImage(true, weekly1, weekly2)
    val img = mergeImage(false, img1, img2)
    image.save(img,false)
    return "查询成功"
}