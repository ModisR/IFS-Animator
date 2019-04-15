package fractals

import scala.annotation.tailrec

class LazyTree[A](root: A, next: Seq[A=>A], leaf: A=>Boolean)
{
  def foreach(maxDepth: Int, doBran: A=>Unit, doLeaf: A=>Unit):Unit = {
    @tailrec
    def foreachImpl(
                     depth: Int,
                     stack: Seq[(A, Int)]
                   ): Unit =
      stack match {
        case (elem, dir) +: prevStack =>
          val (newDepth, newStack) =
            next lift dir match {
              case Some(getNext) =>
                if (leaf(elem) || depth >= maxDepth) {
                  doLeaf(elem)
                  (depth, (elem, dir + 1) +: prevStack)
                } else {
                  doBran(elem)
                  (depth + 1, (getNext(elem), 0) +: stack)
                }
              case None =>
                prevStack match {
                  case (prevElem, prevDir) +: oldStack =>
                    (depth - 1, (prevElem, prevDir + 1) +: oldStack)
                  case _ => return
                }
            }
          foreachImpl(newDepth, newStack)
      }

    (if (maxDepth > 0) doBran else doLeaf) (root)

    foreachImpl(0, Seq(root -> 0))
  }
}