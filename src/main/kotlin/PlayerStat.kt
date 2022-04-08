import com.google.gson.Gson
import pers.shennoter.ApexResponseError
import pers.shennoter.RankLookUp
import java.awt.*
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import java.net.URL
import javax.imageio.ImageIO


fun playerStat(playerid: String): String{
    val apiKey = File("./config/pers.shennoter.ranklookup/apikey.yml").readLines()[0]
    var requestStr = ""
    var id = playerid
    if("@@" in playerid){
        id = playerid.replace("@@", "%20")
    }
    var code = "查询成功"

    try {
        val url = "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=$id&auth=$apiKey"
        requestStr = URL(url).readText()

    }catch(e: FileNotFoundException){
        code = "查询出错：Player exists but has never played Apex Legends"
        RankLookUp.logger.error(code)
        return code
    }catch (e:Exception){
        code = "错误，短时间内请求过多,请稍后再试"
        RankLookUp.logger.error(code)
        return code
    }

    if (requestStr.contains("Error")){
        var errorInfo = ""
        val res = Gson().fromJson(requestStr, ApexResponseError::class.java)
        errorInfo = "查询出错：" + res.Error
        return errorInfo
    }
    //获取json
    val res = Gson().fromJson(requestStr, ApexResponsePlayer::class.java)

    val file = File("./data/pers.shennoter.ranklookup/apex.png")
    if(!file.exists()){
        val background: BufferedImage = ImageIO.read(URL("https://shennoter.top/wp-content/uploads/mirai/apex.png"))
        ImageIO.write(background,"png", File("./data/pers.shennoter.ranklookup/apex.png"))
    }
    val background: BufferedImage = ImageIO.read(File("./data/pers.shennoter.ranklookup/apex.png"))
    val rank: BufferedImage = ImageIO.read(URL(res.global.rank.rankImg))
    val arena: BufferedImage = ImageIO.read(URL(res.global.arena.rankImg))
    val legend: BufferedImage = ImageIO.read(URL(res.legends.selected.ImgAssets.banner))

    var img = drawTextToImage(background,"${res.global.rank.rankScore}",541,800,70,Color.white)
    //图片渲染
    img = drawImageToImage(img,rank,260,260,410,460)
    img = drawImageToImage(img,arena,260,260,1280,450)
    img = drawImageToImage(img,legend,1980,567,0,1050)
    //文字渲染
    img = drawTextToImage(img, "${res.global.arena.rankScore}",1445,795,70,Color.white)
    img = drawTextToImage(img,res.global.name, 455,200,100,Color.white)
    img = drawTextToImage(img,"${res.global.level}", 181,336,35,Color.black)
    if (res.global.rank.ladderPosPlatform == -1){
        img = drawTextToImage(img, "无", 668, 905, 70, Color.white)
    }
    else {
        img = drawTextToImage(img, "#" + "${res.global.rank.ladderPosPlatform}", 500, 695, 35, Color.white)
        img = drawTextToImage(img,"${res.global.rank.ladderPosPlatform}",668,905,70,Color.white)
    }

    if (res.global.arena.ladderPosPlatform == -1){
        img = drawTextToImage(img, "无",1497,905,70,Color.white)
    }
    else {
        img = drawTextToImage(img, "${res.global.arena.ladderPosPlatform}",1497,905,70,Color.white)
        img = drawTextToImage(img,"#"+"${res.global.arena.ladderPosPlatform}",1375,685,35,Color.white)
    }

    //追踪器处理
    var kills = 1
    var damage = 1
    var flag1 = false
    var flag2 = false
        if (res.legends.selected.data.isEmpty()){
        img = drawTextToImage(img,"无", 69,1700,50,Color.white)
    }
    else {
        var numberOfTracker = res.legends.selected.data.indexOf(res.legends.selected.data.last())
        for (i in 0..numberOfTracker) {
            img = drawTextToImage(img,res.legends.selected.data[i].name + ":" + "${res.legends.selected.data[i].value}", 69,1700 + i * 80,50,Color.white)
            if (res.legends.selected.data[i].name == "BR Kills"){
                kills = res.legends.selected.data[i].value
                flag1 = true
            }
            if (res.legends.selected.data[i].name == "BR Damage"){
                damage = res.legends.selected.data[i].value
                flag2 =true
            }
        }
        if (flag1 and flag2){
            val quotient = damage / kills
            img = drawTextToImage(img,"伤害击杀比: $quotient", 457,327,40,Color.white)
        }
    }
    var status = ""
    status = if (res.realtime.isOnline == 0){
        "离线"
    }else{
        "在线"
    }
    img = drawTextToImage(img,status, 1820,1700,50,Color.white)
    status = if (res.realtime.isInGame == 0){
        "未在游戏"
    }else{
        "正在游戏"
    }
    img = drawTextToImage(img,status, 1720,1780,50,Color.white)
    status = if (res.realtime.partyFull == 0){
        "小队未满"
    }else{
        "小队已满"
    }
    img = drawTextToImage(img,status, 1720,1860,50,Color.white)

    //创建图片
    ImageIO.write(img,"png", File("./data/pers.shennoter.ranklookup/player.png"))

    return code
}

fun drawTextToImage(image: BufferedImage,
                    text: String,
                    x: Int,
                    y: Int,
                    size:Int,
                    color:Color): BufferedImage {
    // 拿到 Graphics2D 画图对象
    val imageGraphics: Graphics2D = image.createGraphics()
    // 设置高清字体
    imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    imageGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT)
    imageGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
    // 设置文字 color
    imageGraphics.color = color
    // 设置文体 style
    imageGraphics.font = Font("微软雅黑", Font.BOLD, size)
    // 文字上图
    imageGraphics.drawString(text, x, y )
    return image
}

fun drawImageToImage(img1: BufferedImage,
                     img2: BufferedImage,
                     width: Int,
                     height:Int,
                     x: Int,
                     y: Int): BufferedImage {
    val imageGraphics: Graphics2D = img1.createGraphics()
    imageGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    imageGraphics.drawImage(img2.getScaledInstance(width,height,Image.SCALE_SMOOTH),x,y,null)
    return img1
}
