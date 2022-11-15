package utils

import ApexLookUp
import java.io.File

fun createFiles(){
    val folder1 = ApexLookUp.dataFolder
    if(!folder1.exists()) {
        folder1.mkdirs()
    }
    val folder2 = File("${ApexLookUp.dataFolder}/score/")
    if(!folder2.exists()) {
        folder2.mkdirs()
    }
    val folder3 = File("${ApexLookUp.dataFolder}/imgs/")
    if(!folder3.exists()) {
        folder3.mkdirs()
    }
    val listendID = File("${ApexLookUp.dataFolder}/Data.json")
    if (!listendID.exists()) {
        File("${ApexLookUp.dataFolder}/Data.json").writeText("{\"data\":{\"0\":[0]}}")
    }
    val remindingGroups = File("${ApexLookUp.dataFolder}/Reminder.json")
    if (!remindingGroups.exists()) {
        File("${ApexLookUp.dataFolder}/Reminder.json").writeText("{\"data\":[0]}")
    }
    val userFile = File("${ApexLookUp.dataFolder}/Users.json")
    if(!userFile.exists()) {
        File("${ApexLookUp.dataFolder}/Users.json").writeText("{\"data\":{0:[\"0\"]}}")
    }
}