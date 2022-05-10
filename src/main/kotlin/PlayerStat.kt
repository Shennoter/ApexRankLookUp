import com.google.gson.Gson
import pers.shennoter.*
import java.awt.*
import java.awt.image.BufferedImage
import java.io.FileNotFoundException
import java.net.URL


fun playerStat(playerid: String,image: ApexImage): String{
    if(Config.ApiKey == "") {
        return "未填写ApiKey"
    }
    var requestStr = ""
    var id = playerid
    if("@@" in playerid){
        id = playerid.replace("@@", "%20")
    }
    var code = "查询成功"
    try {
        val url = "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=$id&auth=${Config.ApiKey}"
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
    val res = Gson().fromJson(requestStr, ApexResponsePlayer::class.java)
    if (Config.mode == "pic"){
        playerPicturMode(res,playerid,image)
    }
    else{
        code = playerTextMode(res,playerid)
    }
    return code
}

fun playerPicturMode(res:ApexResponsePlayer,playerid : String,image : ApexImage){
    val background: BufferedImage = ImageCache("apex","https://shennoter.top/wp-content/uploads/mirai/apex.png")
    val rank: BufferedImage = ImageCache("rank_"+res.global.rank.rankName+res.global.rank.rankDiv,res.global.rank.rankImg)
    val arena: BufferedImage = ImageCache("arena_"+res.global.arena.rankName+res.global.arena.rankDiv,res.global.arena.rankImg)
    val legend: BufferedImage = ImageCache("legend_"+res.legends.selected.LegendName,res.legends.selected.ImgAssets.banner)
    var name = res.global.name
    if (name == ""){
        name = playerid
    }

    var img = drawTextToImage(background,"${res.global.rank.rankScore}",541,800,70,Color.white)
    //图片渲染
    img = drawImageToImage(img,rank,260,260,410,460)
    img = drawImageToImage(img,arena,260,260,1280,450)
    img = drawImageToImage(img,legend,1980,567,0,1050)
    //文字渲染
    img = drawTextToImage(img, "${res.global.arena.rankScore}",1445,795,70,Color.white)
    img = drawTextToImage(img,name, 455,200,100,Color.white)
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
                kills = res.legends.selected.data[i].value.toInt()
                flag1 = true
            }
            if (res.legends.selected.data[i].name == "BR Damage"){
                damage = res.legends.selected.data[i].value.toInt()
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
    image.save(img)
}

fun playerTextMode(res:ApexResponsePlayer,playerid : String):String{
    var name = res.global.name
    if (name == ""){
        name = playerid
    }

    var player = ""
    player += "名称:$name\n"
    player += "等级:" + res.global.level + "\n"
    player += "--------排位---------" + "\n"
    player += "段位:" + res.global.rank.rankName + "\n"
    player += "分数:" + res.global.rank.rankScore + "\n"
    player += "--------竞技场--------" + "\n"
    player += "段位:" + res.global.arena.rankName + "\n"
    player += "竞技场分:" + res.global.arena.rankScore + "\n"
    player += "--------状态----------" + "\n"
    player += if (res.realtime.isOnline == 0){
        "在线状态：离线"  + "\n"
    }else{
        "在线状态：在线" + "\n"
    }
    player += if (res.realtime.isInGame == 0){
        "游戏状态：未在游戏" + "\n"
    }else{
        "游戏状态：游戏中" + "\n"
    }
    player += if (res.realtime.partyFull == 0){
        "队伍状态：空闲" + "\n"
    }else{
        "队伍状态：已满" + "\n"
    }
    player += "----------传奇------------" + "\n"
    player += "当前使用:" + res.legends.selected.LegendName + "\n"
    player += "追踪器:" + "\n"
    if (res.legends.selected.data.isEmpty()){
        player += "无"
    }
    else {
        var numberOfTracker = res.legends.selected.data.indexOf(res.legends.selected.data.last())
        for (i in 0..numberOfTracker) {
            player += res.legends.selected.data[i].name + ":" + res.legends.selected.data[i].value + "\n"
        }
    }
    return player
}