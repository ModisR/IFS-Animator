package object linear_algebra {
  import scala.math.{cos, sin, sqrt}

  def pol(r: Double, φ: Double): Vector = (r*cos(φ), r*sin(φ))

  implicit class LinearMap(val matrix: ((Double, Double), (Double, Double))) {
    val (m1@(m11, m12), m2@(m21, m22)) = matrix

    def *(that: LinearMap): LinearMap = (
      (m1*(that.m11, that.m21), m1*(that.m12, that.m22)),
      (m2*(that.m11, that.m21), m2*(that.m12, that.m22))
    )

    def *(vector: Vector): Vector =
      (m1 * vector, m2* vector)

    def det: Double = m11 * m22 - m12 * m21
  }

  implicit class Vector(val vector: (Double, Double)) {
    val (x, y) = vector

    def *(that: Vector): Double = x * that.x + y * that.y

    def abs: Double = sqrt(x*x + y*y)

    def +(that: Vector): Vector = (x + that.x, y + that.y)
    def -(that: Vector): Vector = (x - that.x, y - that.y)
  }

  implicit class Scalar(n: Double)
  {
    def *(matrix: LinearMap): LinearMap = (
      (n * matrix.m11, n * matrix.m12),
      (n * matrix.m21, n * matrix.m22)
    )

    def *(vector: Vector): Vector = (n * vector.x, n * vector.y)
  }

  case class Line (a: Vector, b: Vector) {
    def length: Double = a - b abs
  }

  /** Common linear transformations**/
  case object Identity extends LinearMap(
    (1,0),
    (0,1)
  )

  case class Scale(
                    λ: Double
                  ) extends LinearMap(
    (λ,0),
    (0,λ))

  case class Rotate(
                     θ: Double
                   ) extends LinearMap(
    (cos(θ), -sin(θ)),
    (sin(θ),  cos(θ))
  )

  case class Reflect(
                      θ: Double
                    ) extends LinearMap(
    (cos(2*θ),  sin(2*θ)),
    (sin(2*θ), -cos(2*θ))
  )
}