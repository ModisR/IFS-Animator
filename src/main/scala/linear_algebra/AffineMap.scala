package linear_algebra

case class AffineMap(
                      map  : LinearMap,
                      trans: Vector = (0D, 0D)
                    ){
  def apply(line: Line): Line =
    Line (apply(line.a), apply(line.b))

  def apply(vector: Vector): Vector =
    map*vector + trans

  def this(map: LinearMap, pivot: Vector, trans: Vector) =
    this(map, trans + pivot - map*pivot)
}