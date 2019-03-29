import java.awt.Graphics2D

import fractals.SierpinskiTriangle
import linear_algebra.{Rotate, Scalar, Vector, pol}

import scala.math.{pow, Pi => π}

case class SierpinskiFold (pA: Vector, pB: Vector)
  extends Animation ("sierpinski-fold", 960, 540, 24, 18, 9)
{
  def wax(g: Graphics2D, iter: Int, prog: Double)  { draw(g, waxing =  true, iter, prog) }
  def wane(g: Graphics2D, iter: Int, prog: Double) { draw(g, waxing = false, iter, prog) }

  private val size = pA - pB abs
  private val pC: Vector = pA + .5*Rotate(-π/3)*(pB-pA)
  private val pD: Vector = pB + .5*Rotate(π/3)*(pA-pB)

  private def draw(g: Graphics2D, waxing: Boolean, iter: Int, prog: Double) {
    val p1: Vector = (1 - prog) * pA + prog * pC
    val p2: Vector = (1 - prog) * pB + prog * pD

    val s = size*pow(.5, prog)
    val ang = 2*π/3*prog
    val odd = iter % 2 == 1

    val (p0, p3) =
      if (waxing ^ odd) (
        p1 + pol(s, -2*ang),
        p2 + pol(s, 2*ang-π)
      )
      else (
        p1 + pol(s, ang),
        p2 + pol(s, π-ang)
      )

    Seq (
      p1 -> p0,
      p1 -> p2,
      p3 -> p2
    ) foreach { case (a, b) =>
      SierpinskiTriangle(a, b) plot (g, iter)
    }
  }
}
