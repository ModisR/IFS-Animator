import java.awt.{Color, Graphics2D}
import java.awt.geom.Line2D

import fractals.BarnsleyFern
import linear_algebra.{AffineMap, Line, Rotate, Scale, pol}

import scala.math.{asin, pow, sqrt, Pi => π}

case class GrowingFern(name: String, w: Int, h: Int)
  extends Animation (name, w, h, 24, 32, 16)
{
  private val bot = (180d, 640d)
  private val leaf1dis = 80

  private val a = pol(leaf1dis, π*5/6) + bot
  private val b = pol(3*leaf1dis, -π/6) + bot

  private val stem1 = Line (a, b)
  private val fern1 = BarnsleyFern (stem1)

  def wax (g: Graphics2D, iter: Int, prog: Double): Unit = {
    def short(l: Line): Line = Line(l.a, prog*l.b + (1-prog)*l.a)
    def draw(l: Line) {g draw new Line2D.Double(l.a.x, l.a.y, l.b.x, l.b.y)}

    fern1.lines foreach (iter + 1, draw, {l => draw (short (l))})
  }

  private val dis = 3/sqrt(7) * stem1.length/12
  private val bea = -asin(sqrt(21)/14) - π/2
  private val pivot = pol (dis, bea) + bot

  def wane(g: Graphics2D, iter: Int, prog: Double): Unit = {
    val size = pow(3, 1-prog)
    val ang = (1-prog)*π/3

    val lin = Scale(size)*Rotate(ang)
    val aff = new AffineMap(lin, pivot, (0D, 0D))

    val stem = aff (stem1)

    BarnsleyFern(stem) plot (g, iter + 1)

    g setColor Color.BLACK
    g fillRect (0, 610, 60, 640)
  }
}