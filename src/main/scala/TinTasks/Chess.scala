package TinTasks

import scala.io.StdIn

object Chess {

  def main(args: Array[String]): Unit = {
    val line1 = StdIn.readLine()
    val line2 = StdIn.readLine()
    val line3 = StdIn.readLine()
    val line4 = StdIn.readLine()
    val line5 = StdIn.readLine()
    val line6 = StdIn.readLine()
    val line7 = StdIn.readLine()
    val line8 = StdIn.readLine()

    var source: Array[Array[Char]] = Array[String](line1, line2, line3, line4, line5, line6, line7, line8).map(_.toCharArray)

    var i = 0
    var j = 0

    while (i < source.length) {
      while (j < source.length) {
        if (source(i)(j) == 'R') {
          source = doLad(source, i, j)
        } else if (source(i)(j) == 'B') {
          source = doSlon(source, i, j)
        }
        j = j + 1
      }
      j = 0
      i = i + 1
    }

    println(calculateResult(source))
  }

  def doLad(source: Array[Array[Char]], vert: Int, gor: Int): Array[Array[Char]] = {
    var i = 0
    var j = 0
    while (i < source.length) {
      while (j < source.length) {
        if (i == vert) {
          source(i).update(j, 'X')
        } else if (j == gor) {
          source(i).update(j, 'X')
        }
        j = j + 1
      }
      j = 0
      i = i + 1
    }
    source
  }

  def doSlon(source: Array[Array[Char]], vert: Int, gor: Int): Array[Array[Char]] = {
    var i = vert
    var j = gor
    source(vert).update(gor, 'X')

    while (i - 1 >= 0 && i - 1 < source.length && j - 1 >= 0 && j - 1 < source.length) {
      source(i - 1).update(j - 1, 'X')
      i = i - 1
      j = j - 1
    }

    i = vert
    j = gor
    while (i + 1 >= 0 && i + 1 < source.length && j + 1 >= 0 && j + 1 < source.length) {
      source(i + 1).update(j + 1, 'X')
      i = i + 1
      j = j + 1
    }
    i = vert
    j = gor
    while (i - 1 >= 0 && i - 1 < source.length && j + 1 >= 0 && j + 1 < source.length) {
      source(i - 1).update(j + 1, 'X')
      i = i - 1
      j = j + 1
    }
    i = vert
    j = gor
    while (i + 1 >= 0 && i + 1 < source.length && j - 1 >= 0 && j - 1 < source.length) {
      source(i + 1).update(j - 1, 'X')
      i = i + 1
      j = j - 1
    }
    source
  }

  def calculateResult(source: Array[Array[Char]]): Int = {
    source.map(_.mkString("")).mkString("").count(char => char == '*')
  }
}
