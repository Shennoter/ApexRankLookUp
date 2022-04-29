import com.google.gson.Gson
import pers.shennoter.ApexImage
import pers.shennoter.ApexResponseCraft
import pers.shennoter.Config
import pers.shennoter.RankLookUp
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO


fun craftStat(image: ApexImage):String{
    if(Config.ApiKey == "") {
        return "未填写ApiKey"
    }
    var requestStr = ""
    var code = "查询成功"

    try {
        val url = "https://api.mozambiquehe.re/crafting?&auth=${Config.ApiKey}"
        requestStr = URL(url).readText()
    }catch (e:Exception){
        code = "错误，短时间内请求过多,请稍后再试"
        RankLookUp.logger.error(code)
        return code
    }

    val res = Gson().fromJson(requestStr, ApexResponseCraft::class.java)
    val daily1: BufferedImage = ImageIO.read(URL(res[0].bundleContent[0].itemType.asset))
    val daily2: BufferedImage = ImageIO.read(URL(res[0].bundleContent[1].itemType.asset))
    val weekly1: BufferedImage = ImageIO.read(URL(res[1].bundleContent[0].itemType.asset))
    val weekly2: BufferedImage = ImageIO.read(URL(res[1].bundleContent[1].itemType.asset))
    val img1: BufferedImage = mergeImage(true, daily1, daily2)
    val img2: BufferedImage = mergeImage(true, weekly1, weekly2)
    val img = mergeImage(false, img1, img2)
    image.save(img)
    return code
}

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
        var ImageArrayOne: IntArray? = IntArray(w1 * h1)
        ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1) // 逐行扫描图像中各个像素的RGB到数组中
        if (isHorizontal) { // 水平方向合并
            destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1) // 设置上半部分或左半部分的RGB
        } else { // 垂直方向合并
            destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1) // 设置上半部分或左半部分的RGB
        }
        wx += w1
        wy += h1
    }
    return destImage
}