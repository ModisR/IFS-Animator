import java.awt.RenderingHints.{KEY_ANTIALIASING, VALUE_ANTIALIAS_ON}
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.awt.{Color, Graphics2D}
import java.io.File

import javax.imageio.ImageIO

import scala.math.{cos, Pi => π}
import scala.sys.process._

abstract class Animation(
                          name: String,
                          w: Int,
                          h: Int,
                          fps: Int,
                          duration: Int,
                          iterations: Int
                        ){
  val frames: Int = duration * fps

  def render(col: Color, start: Int = 0, end: Int = frames) {

    start until end foreach { n =>
      val img = new BufferedImage(w, h, TYPE_INT_RGB)
      val g = img createGraphics()

      g setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)

      g setColor col

      val animProg = iterations * (1 - cos (2*π*n/frames))/2
      val iterNum  = animProg toInt
      val iterProg = animProg - iterNum

      {
        if (n < frames / 2D) wax _
        else wane _
      } tupled (g, iterNum, iterProg)

      g dispose()

      ImageIO write(img, "png", new File(f"$name$n%04d.png"))
    }

    /** You need ImageMagick for this bit */
    s"convert -delay ${100/fps} $name*.png $name.gif"!
  }

  def wax(g: Graphics2D, iter: Int, prog: Double): Unit
  def wane(g: Graphics2D, iter: Int, prog: Double): Unit
}