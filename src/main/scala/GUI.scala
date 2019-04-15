import java.awt.{Color, Dimension}

import fractals.BarnsleyFern
import linear_algebra.Line

import scala.swing.{BoxPanel, Button, Component, Graphics2D, MainFrame, Orientation}

class GUI extends MainFrame{
  val w = 1280
  val h = 720
  val dim = new Dimension(w, h)

  title = "IFS Plotter"

  contents = new BoxPanel(Orientation.Vertical) {
    contents ++= Seq (
      new Component {
        preferredSize = dim

        override def paintComponent(g: Graphics2D) {
          val a = (w toDouble, h / 2.0)
          val b = (w * 5 / 6.0, h / 2.0)

          val stem = Line(a, b)
          val fern = BarnsleyFern(stem)

          g setColor Color.BLACK
          g fillRect(0, 0, w, h)

          g setColor Color.GREEN
          fern.plot(g, 99)
        }
      }
      ,
      Button("Save Image") {
        println("Hi!")
      }
    )
  }

  visible = true
}