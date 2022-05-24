package pers.shennoter

import java.awt.*
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO

class ApexImage {
    var isEmpty = true
    private val image = ByteArrayOutputStream()
    fun save(buffImage: BufferedImage) {
        ImageIO.write(buffImage, Config.picType, image)
        isEmpty = false
    }

    fun get(): ByteArrayInputStream {
        return (ByteArrayInputStream(image.toByteArray()))
    }
}

fun ImageCache(name: String, url: String): BufferedImage {
    val file = File("./data/pers.shennoter.ranklookup/$name.png")
    if (!file.exists()) {
        val image: BufferedImage = ImageIO.read(URL(url))
        ImageIO.write(image, "png", File("./data/pers.shennoter.ranklookup/$name.png"))
    }
    return ImageIO.read(File("./data/pers.shennoter.ranklookup/$name.png"))
}

//删除缓存文件
fun removeFileByTime(isAuto: Boolean) :String{
    //获取目录下所有文件
    val dirPath = "./data/pers.shennoter.ranklookup/"
    val allFile = getDirAllFile(File(dirPath))
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


//将文字添加到图片
fun drawTextToImage(image: BufferedImage,
                    text: String,
                    x: Int,
                    y: Int,
                    size:Int,
                    color: Color
): BufferedImage {
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


//将图片添加到另一个图片
fun drawImageToImage(img1: BufferedImage,
                     img2: BufferedImage,
                     width: Int,
                     height:Int,
                     x: Int,
                     y: Int): BufferedImage {
    val imageGraphics: Graphics2D = img1.createGraphics()
    imageGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    imageGraphics.drawImage(img2.getScaledInstance(width,height, Image.SCALE_SMOOTH),x,y,null)
    return img1
}


//拼接图片
fun mergeImage(isHorizontal: Boolean, vararg imgs: BufferedImage): BufferedImage {
    // 生成新图片
    var destImage: BufferedImage? = null
    // 计算新图片的长和高
    var allw = 0
    var allh = 0
    var allwMax = 0
    var allhMax = 0
    // 获取总长、总宽、最长、最宽
    for (i in imgs.indices) {
        val img = imgs[i]
        allw += img.width
        allh += img.height
        if (img.width > allwMax) {
            allwMax = img.width
        }
        if (img.height > allhMax) {
            allhMax = img.height
        }
    }
    // 创建新图片
    destImage = if (isHorizontal) {
        BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB)
    } else {
        BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB)
    }
    // 合并所有子图片到新图片
    var wx = 0
    var wy = 0
    for (i in imgs.indices) {
        val img = imgs[i]
        val w1 = img.width
        val h1 = img.height
        // 从图片中读取RGB
        var imageArrayOne: IntArray? = IntArray(w1 * h1)
        imageArrayOne = img.getRGB(0, 0, w1, h1, imageArrayOne, 0, w1) // 逐行扫描图像中各个像素的RGB到数组中
        if (isHorizontal) { // 水平方向合并
            destImage.setRGB(wx, 0, w1, h1, imageArrayOne, 0, w1) // 设置上半部分或左半部分的RGB
        } else { // 垂直方向合并
            destImage.setRGB(0, wy, w1, h1, imageArrayOne, 0, w1) // 设置上半部分或左半部分的RGB
        }
        wx += w1
        wy += h1
    }
    return destImage
}
