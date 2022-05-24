package pers.shennoter

import pers.shennoter.RankLookUp.dataFolder
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

//删除缓存文件
fun removeFileByTime(isAuto: Boolean) :String{
    //获取目录下所有文件
    val allFile = getDirAllFile(File("$dataFolder/imgs"))
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    //获取当前时间
    var end = Date(System.currentTimeMillis())
    try {
        end = dateFormat.parse(dateFormat.format(Date(System.currentTimeMillis())))
    } catch (e: Exception) {
        return "缓存文件日期格式错误"
    }
    for (file in allFile) { //ComDef
        try {
            //文件时间减去当前时间
            val start: Date = dateFormat.parse(dateFormat.format(Date(file.lastModified())))
            val diff: Long = end.time - start.time //这样得到的差值是微秒级别
            val days = diff / (1000 * 60 * 60 * 24)
            if(isAuto){
                if (Config.cacheExpireTime <= days) { //删除cacheExpireTime天前的缓存文件
                    deleteFile(file)
                }
            }
            else{
                deleteFile(file)
            }
        } catch (e: Exception) {
            return "删除失败"
        }
    }
    return "缓存删除成功"
}

//删除文件夹及文件夹下所有文件
fun deleteFile(file: File) {
    if (file.isDirectory) {
        val files = file.listFiles()
        for (i in files.indices) {
            val f = files[i]
            deleteFile(f)
        }
        file.delete()
    } else if (file.exists()) {
        file.delete()
    }
}

//获取指定目录下一级文件
fun getDirAllFile(file: File): List<File> {
    val fileList: MutableList<File> = ArrayList()
    val fileArray = file.listFiles() ?: return fileList
    for (f in fileArray) {
        fileList.add(f)
    }
    return fileList
}