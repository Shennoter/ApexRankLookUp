package pers.shennoter

import com.google.gson.Gson
import drawImageToImage
import drawTextToImage
import mergeImage
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import javax.imageio.ImageIO


fun mapStat(image: ApexImage):String{
    if(Config.ApiKey == "") {
        return "未填写ApiKey"
    }
    var requestStr = ""
    var code = "查询成功"

    try {
        val url = "https://api.mozambiquehe.re/maprotation?version=2&auth=${Config.ApiKey}"
        requestStr = URL(url).readText()
    }catch (e:Exception){
        code = "错误，短时间内请求过多,请稍后再试"
        RankLookUp.logger.error(code)
        return code
    }
    var textinfo = ""
    val res = Gson().fromJson(requestStr, ApexResponseMap::class.java)


    if (Config.mode == "pic"){
        mapPictureMode(res, image)
    }
    else{
        textinfo = mapTextMode(res)
        code = "$code\n===================\n$textinfo"
    }

    return code
}

fun mapPictureMode(res: ApexResponseMap, image: ApexImage){
    val buffer:BufferedImage = ImageIO.read(URL("https://apexlegendsstatus.com/assets/maps/Arena_Encore.png"))
    val battleRoyale: BufferedImage = ImageIO.read(URL(res.battle_royale.current.asset))
    val ranked:BufferedImage = ImageIO.read(URL(res.ranked.current.asset))
    val arenas: BufferedImage = ImageIO.read(URL(res.arenas.current.asset))
    val arenasRanked: BufferedImage = ImageIO.read(URL(res.arenasRanked.current.asset))
    //val control:BufferedImage = ImageIO.read(URL(res.control.current.asset))

    //渲染背景
    val background = mergeImage(false,buffer,buffer,buffer,buffer)
    var img = drawImageToImage(background, battleRoyale,1920,600,0,0)
    img = drawImageToImage(img, ranked,1920,600,0,600)
    img = drawImageToImage(img,arenas,1920,600,0,1200)
    img = drawImageToImage(img,arenasRanked,1920,600,0,1800)

    //匹配
    img = drawTextToImage(img,"匹配",80,200,150, Color.white)
    img = drawTextToImage(img,"${res.battle_royale.current.map}",80,350,150, Color.white)
    img = drawTextToImage(img,"剩余时间："+"${res.battle_royale.current.remainingTimer}",90,450,35, Color.white)
    img = drawTextToImage(img,"下一轮换："+"${res.battle_royale.next.map}",90,500,35, Color.white)
    //排位
    img = drawTextToImage(img,"排位",80,800,150, Color.white)
    img = drawTextToImage(img,"${res.ranked.current.map}",80,950,150, Color.white)
    img = drawTextToImage(img,"下一轮换："+"${res.ranked.next.map}",90,1050,35, Color.white)
    //竞技场
    img = drawTextToImage(img,"竞技场",80,1400,150, Color.white)
    img = drawTextToImage(img,"${res.arenas.current.map}",80,1550,150, Color.white)
    img = drawTextToImage(img,"剩余时间："+"${res.arenas.current.remainingTimer}",90,1650,35, Color.white)
    img = drawTextToImage(img,"下一轮换："+"${res.arenas.next.map}",90,1700,35, Color.white)
    //排位竞技场
    img = drawTextToImage(img,"排位竞技场",80,2000,150, Color.white)
    img = drawTextToImage(img,"${res.arenasRanked.current.map}",80,2150,150, Color.white)
    img = drawTextToImage(img,"剩余时间："+"${res.arenasRanked.current.remainingTimer}",90,2250,35, Color.white)
    img = drawTextToImage(img,"下一轮换："+"${res.arenasRanked.next.map}",90,2300,35, Color.white)

    image.save(img)
}

fun mapTextMode(res:ApexResponseMap):String{
    var map = ""
    map += "----------匹配------------" + "\n"
    map += "当前地图:" + res.battle_royale.current.map + "\n"
    map += "剩余时间:" + res.battle_royale.current.remainingTimer + "\n"
    map += "下一轮换:" + res.battle_royale.next.map + "\n"
    map += "---------竞技场-----------" + "\n"
    map += "当前地图:" + res.arenas.current.map + "\n"
    map += "剩余时间:" + res.arenas.current.remainingTimer + "\n"
    map += "下一轮换:" + res.arenas.next.map + "\n"
    map += "----------排位------------" + "\n"
    map += "当前地图:" + res.ranked.current.map + "\n"
    map += "下一轮换:" + res.ranked.next.map + "\n"
    map += "--------排位竞技场--------" + "\n"
    map += "当前地图:" + res.arenasRanked.current.map + "\n"
    map += "剩余时间:" + res.arenasRanked.current.remainingTimer + "\n"
    map += "下一轮换:" + res.arenasRanked.next.map
    return map
}