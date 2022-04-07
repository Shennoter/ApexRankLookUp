package pers.shennoter

import com.google.gson.Gson
import drawTextToImage
import mergeImage
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import javax.imageio.ImageIO


fun mapStat():String{
    val apiKey = File("./config/pers.shennoter.ranklookup/apikey.yml").readLines()[0]
    var requestStr = ""
    var code = "查询成功"
    try {
        val url = "https://api.mozambiquehe.re/maprotation?version=2&auth=$apiKey"
        requestStr = URL(url).readText()
    }catch (e:Exception){
        val excp = e.toString()
        if (":" in excp){
            code = "错误，短时间内请求过多,请稍后再试"
        }
        RankLookUp.logger.error(code)
        return code
    }
    val res = Gson().fromJson(requestStr, ApexResponseMap::class.java)

    val battleRoyale: BufferedImage = ImageIO.read(URL(res.battle_royale.current.asset))
    val ranked:BufferedImage = ImageIO.read(URL(res.ranked.current.asset))
    //val control:BufferedImage = ImageIO.read(URL(res.control.current.asset))
    //val arenas: BufferedImage = ImageIO.read(URL(res.arenas.current.asset))
    //val arenasRanked: BufferedImage = ImageIO.read(URL(res.arenasRanked.current.asset))
    val background = mergeImage(false,battleRoyale,ranked)

    var img = drawTextToImage(background,"${res.battle_royale.current.map}",197,650,300, Color.white)
    img = drawTextToImage(img,"匹配",197,350,300, Color.white)
    img = drawTextToImage(img,"结束时间："+"${res.battle_royale.current.readableDate_end}",197,900,70, Color.white)
    img = drawTextToImage(img,"下一轮换："+"${res.battle_royale.next.map}",197,1000,70, Color.white)

    img = drawTextToImage(img,"排位",197,1550,300, Color.white)
    img = drawTextToImage(img,"${res.ranked.current.map}",197,1900,300, Color.white)
    img = drawTextToImage(img,"下一轮换："+"${res.ranked.next.map}",197,2100,70, Color.white)

    ImageIO.write(img,"png", File("./data/pers.shennoter.ranklookup/map.png"))

    return code
}