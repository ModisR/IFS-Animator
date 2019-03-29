package fractals

import scala.annotation.tailrec

class LazyTree[A](root: A, next: Seq[A=>A], leaf: A=>Boolean)
{
  def foreach(maxDepth: Int, doBran: A=>Unit, doLeaf: A=>Unit):Unit = {
    @tailrec
    def foreachImpl(
                  depth: Int,
                  stack: Seq[(Int, A)]
            ): Unit =
      stack match {
        case (dir, elem) +: oldStack =>
          val (newDepth, newStack) =
            if (dir >= next.length) {
              oldStack match {
                case (prevDir, prevElem) +: prevStack =>
                  (depth - 1, (prevDir + 1, prevElem) +: prevStack)
                case _ => return
              }
            }
            else {
              if (leaf(elem) || depth > maxDepth) {
                doLeaf(elem)
                (depth, (dir + 1, elem) +: oldStack)
              } else {
                doBran(elem)
                (depth + 1, (0, next(dir)(elem)) +: stack)
              }
            }
          foreachImpl(newDepth, newStack)
      }
    doBran (root)
    foreachImpl(1, Seq(0 -> root))
  }
}