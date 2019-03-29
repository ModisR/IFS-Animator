import java.awt.Color

object IFS extends App
{
  // image dimensions
  val h = 640
  val w = 360

  val fern = GrowingFern("growing-fern", w, h)
  fern render Color.GREEN
}