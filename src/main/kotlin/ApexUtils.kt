package pers.shennoter

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

class ApexImage {
    var isEmpty = true
    private val image = ByteArrayOutputStream()
    fun save(buffImage: BufferedImage) {
        ImageIO.write(buffImage, "png", image)
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