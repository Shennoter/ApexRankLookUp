package utils

import pers.shennoter.RankLookUp
import java.io.File

fun createFiles(){
    val folder1 = File("./data/pers.shennoter.RankLookUp/")
    if(!folder1.exists()) {
        folder1.mkdirs()
    }
    val folder2 = File("${RankLookUp.dataFolder}/score/")
    if(!folder2.exists()) {
        folder2.mkdirs()
    }
    val folder3 = File("${RankLookUp.dataFolder}/imgs/")
    if(!folder3.exists()) {
        folder3.mkdirs()
    }
    val listendID = File("${RankLookUp.dataFolder}/Data.json")
    if (!listendID.exists()) {
        File("${RankLookUp.dataFolder}/Data.json").writeText("{\"data\":{\"0\":[0]}}")
    }
    val remindingGroups = File("${RankLookUp.dataFolder}/Reminder.json")
    if (!remindingGroups.exists()) {
        File("${RankLookUp.dataFolder}/Reminder.json").writeText("{\"data\":[0]}")
    }
    val userFile = File("${RankLookUp.dataFolder}/Users.json")
    if(!userFile.exists()) {
        File("${RankLookUp.dataFolder}/Users.json").writeText("{\"data\":{0:[\"0\"]}}")
    }
}