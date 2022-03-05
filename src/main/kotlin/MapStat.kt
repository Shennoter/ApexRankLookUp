package pers.shennoter

import com.google.gson.Gson
import java.net.URL


fun mapStat():String{
    var requestStr = ""
    try {
        val url = "https://api.mozambiquehe.re/maprotation?version=2&auth=FsKmkWPMRJlOEmW8H3ZN"
        requestStr = URL(url).readText()
    }catch (e:Exception){
        RankLookUp.logger.error("查询出错")
        return "查询失败"
    }
    val res = Gson().fromJson(requestStr, ApexResponseMap::class.java)
    var map = ""
    map += "----------匹配------------" + "\n"
    map += "当前地图:" + res.battle_royale.current.map + "\n"
    map += "剩余时间:" + res.battle_royale.current.remainingTimer + "\n"
    map += "下一轮换:" + res.battle_royale.next.map + "\n"
    //map += res.battle_royale.current.asset
    map += "---------竞技场-----------" + "\n"
    map += "当前地图:" + res.arenas.current.map + "\n"
    map += "剩余时间:" + res.arenas.current.remainingTimer + "\n"
    map += "下一轮换:" + res.arenas.next.map + "\n"
    //map += res.arenas.current.asset
    map += "----------排位------------" + "\n"
    map += "当前地图:" + res.ranked.current.map + "\n"
    map += "下一轮换:" + res.ranked.next.map + "\n"
    //map += res.ranked.current.asset
    map += "--------排位竞技场--------" + "\n"
    map += "当前地图:" + res.arenasRanked.current.map + "\n"
    map += "剩余时间:" + res.arenasRanked.current.remainingTimer + "\n"
    map += "下一轮换:" + res.arenasRanked.next.map
    return map
}