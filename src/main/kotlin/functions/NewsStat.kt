import com.google.gson.Gson
import pers.shennoter.*
import java.awt.image.BufferedImage
import java.net.URL

fun newsStat(image: ApexImage,index:Int):String{
    if(Config.ApiKey == "") {
        return "未填写ApiKey"
    }
    var requestStr = ""
    var code = "查询成功"
    try {
        val url = "https://api.mozambiquehe.re/news?&auth=${Config.ApiKey}"
        requestStr = URL(url).readText()
    }catch (e:Exception){
        code = "错误，短时间内请求过多,请稍后再试"
        RankLookUp.logger.error(code)
        return code
    }
    val res = Gson().fromJson(requestStr, ApexResponseNews::class.java)
    if(index < 1 || index > res.size){
        return "序号无效"
    }
    if(index-1 <= res.size) {
        val img: BufferedImage = ImageCache("news_" + res[index-1].short_desc.hashCode(), res[index-1].img)
        val title = res[index-1].title
        val link = res[index-1].link
        val digest = res[index-1].short_desc
        code = "标题：$title\n"
        code += "链接：$link\n"
        code += "摘要:$digest\n"
        code += "序号：$index/${res.size}"
        image.save(img)
    }
    return code
}