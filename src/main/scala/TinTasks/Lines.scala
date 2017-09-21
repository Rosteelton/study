package TinTasks

import Math._

import scala.io.StdIn

object Lines {

  case class Point(x: Int, y: Int)

  def main(args: Array[String]): Unit = {

    val line1 = StdIn.readLine().split(" ").map(_.toInt)
    val line2 = StdIn.readLine().split(" ").map(_.toInt)

    val a1 = Point(line1(0), line1(1))
    val a2 = Point(line1(2), line1(3))
    val b1 = Point(line2(0), line2(1))
    val b2 = Point(line2(2), line2(3))

    doIntersect(a1, a2, b1, b2) match {
      case true => println("YES")
      case false => println("NO")
    }
  }

  def onSegment(p: Point, q: Point, r: Point): Boolean = {
    if (q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) && q.y <= max(p.y, r.y) && q.y >= min(p.y, r.y)) {
      true
    } else {
      false
    }
  }

  def orientation(p: Point, q: Point, r: Point): Int = {
    val some = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)
    if (some == 0) 0
    else if (some > 0) {
      1
    } else 2
  }

  def doIntersect(p1: Point, q1: Point, p2: Point, q2: Point): Boolean = {
    val o1 = orientation(p1, q1, p2)
    val o2 = orientation(p1, q1, q2)
    val o3 = orientation(p2, q2, p1)
    val o4 = orientation(p2, q2, q1)

    if (o1 != o2 && o3 != o4) {
      true
    } else if (o1 == 0 && onSegment(p1, p2, q1)) {
      true
    } else if (o2 == 0 && onSegment(p1, q2, q1)) {
      true
    } else if (o3 == 0 && onSegment(p2, p1, q2)) {
      true
    } else if (o4 == 0 && onSegment(p2, q1, q2)) {
      true
    } else false
  }

}
