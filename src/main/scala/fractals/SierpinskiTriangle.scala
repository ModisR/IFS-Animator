package fractals

import linear_algebra.{AffineMap, Line, Rotate, Scale, Vector}

import scala.math.{Pi => π}

case class SierpinskiTriangle(
                               pointA: Vector,
                               pointB: Vector
                             ) extends Fractal(
  Line (pointA, pointB), Array(
    AffineMap(Scale(.5), pointB + Rotate(π/3)*(pointA - pointB)),
    new AffineMap(Scale(.5)*Rotate(π*2/3), pointB, pointA - pointB),
    new AffineMap(Scale(.5)*Rotate(-π*2/3), pointA, pointB - pointA)
  ),
  plotAll = false
)
