package fractals

import linear_algebra.{AffineMap, Line, Reflect, Rotate, Scale}

import scala.math.{atan, Pi => π}

case class BarnsleyFern(
                         stem: Line,
                         stemRatio: Double = 6,
                         stemBend : Double = π/60,
                         leaf1Ratio: Double = 2.9,
                         leaf2Ratio: Double = 3.1,
                         leafAngle: Double = π/6
                       ) extends Fractal (
  stem, {
    val Line(a, b) = stem
    val leaf1Scale = Scale (1/leaf1Ratio)
    val leaf2Scale = Scale (1/leaf2Ratio)
    val stemScale = Scale((stemRatio - 1) / stemRatio)

    val stemAng = atan ( (b.y-a.y) / (b.x-a.x) )
    val refAng = stemAng + π/4-leafAngle/2

    val leaf1Map =  Rotate(leafAngle-π/2) * leaf1Scale
    val leaf2Map = Reflect(refAng) * leaf2Scale
    val bendStem = stemScale * Rotate(stemBend)

    Array(
      new AffineMap (bendStem, a, b - a),
      new AffineMap (leaf1Map, a, .25*(b-a)),
      new AffineMap (leaf2Map, a, .75*(b-a))
    )
  },
  true
)