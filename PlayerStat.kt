package pers.shennoter

import ApexResponsePlayer
import com.google.gson.Gson
import java.net.URL


fun playerStat(playerid: String): String{
    var requestStr = ""
    var id = playerid
    if("@@" in playerid){
        id = playerid.replace("@@", "%20")
    }
    try {
        val url = "https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=$id&auth=FsKmkWPMRJlOEmW8H3ZN"
        requestStr = URL(url).readText()
    }catch (e:Exception){
        RankLookUp.logger.error("查询出错")
        return "查询失败"
    }
    if (requestStr.contains("Error")){
        var errorInfo = ""
        val res = Gson().fromJson(requestStr, ApexResponseError::class.java)
        errorInfo = "查询出错：" + res.Error
        return errorInfo
    }
    val res = Gson().fromJson(requestStr, ApexResponsePlayer::class.java)
    var player = ""
    player += "名称:" + res.global.name + "\n"
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
    player += "--------传奇----------" + "\n"
    player += "当前使用:" + res.legends.selected.LegendName + "\n"
    player += "追踪器:" + "\n"
    var flag1 = false
    var flag2 = false
    var kills = 1
    var damage = 1
    if (res.legends.selected.data.isEmpty()){
        player += "无"
    }
    else {
        var numberOfTracker = res.legends.selected.data.indexOf(res.legends.selected.data.last())
        for (i in 0..numberOfTracker) {
            player += res.legends.selected.data[i].name + ":" + res.legends.selected.data[i].value + "\n"
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
            player += "伤害击杀比：$quotient"
        }
    }
    return player
}