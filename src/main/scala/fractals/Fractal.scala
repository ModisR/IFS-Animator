package fractals

import java.awt.{Color, Graphics2D}
import java.awt.RenderingHints.{KEY_ANTIALIASING, VALUE_ANTIALIAS_ON}
import java.awt.geom.Line2D
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File

import javax.imageio.ImageIO
import linear_algebra.{AffineMap, Line}

class Fractal(
               val axiom: Line,
               val rules: Seq[AffineMap],
               val plotAll: Boolean
             ) {
  def saveImage (name: String, w: Int, h: Int, iter: Int, col: Color = Color.WHITE) {
    val img = new BufferedImage(w, h, TYPE_INT_RGB)
    val g = img createGraphics()

    g setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)

    g setColor col

    plot (g, iter)

    g dispose()

    ImageIO write(img, "png", new File(s"$name.png"))
  }

  def plot(g: Graphics2D, iter: Int): Unit = {
    def draw(l: Line) {g draw new Line2D.Double(l.a.x, l.a.y, l.b.x, l.b.y)}

    lines foreach(iter, if (plotAll) draw else {_=>}, draw)
  }

  def lines: LazyTree[Line] = {
    val maps = rules map (rule => rule.apply(_: Line))
    new LazyTree[Line](axiom, maps, _.length < .1)
  }
}