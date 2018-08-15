import scala.annotation.tailrec
import sun.applet.Main
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

object frac {

  //Constants for x-y range and image resolution.
  //There we have a 3:2 ratio, which fits nicely the whole view of the set
  val Width = 9000
  val Height = 6000
  val MinX = -2
  val MaxX = 1
  val MinY = -1
  val MaxY = 1

  //Number of iterations of the formula
  val Iter = 50

  class Complex(re: Double, im: Double) {
    def real(): Double = {
      re
    }

    def imaginary(): Double = {
      im
    }

    def module(): Double = {
      Math.sqrt(mul(this, this.conj()).real())
    }

    def square(): Complex = {
      mul(this, this)
    }

    def conj(): Complex = {
      new Complex(this.real(), -1 * this.imaginary())
    }
  }
  
  

  def add(z: Complex, w: Complex): Complex = {
    new Complex(z.real() + w.real(), z.imaginary() + w.imaginary())
  }

  def mul(z: Complex, w: Complex): Complex = {
    new Complex(z.real() * w.real() + -1 * z.imaginary() * w.imaginary(), z.real() * w.imaginary() + z.imaginary() * z.real())
  }

  @tailrec def brot(z: Complex, c: Complex, i: Int): Complex = {
    (z, c, i) match {
      case (_, _, 0) => z
      case _ => brot(add(z.square(), c), c, i - 1)
    }
  }

  def step(max: Double, min: Double, res: Double): Option[Double] = {
    (max, min, res) match {
      case (_, _, 0) => None
      case _ =>
        if (max < min) {
          None
        } else {
          Some(Math.abs((max - min) / res))
        }
    }
  }

  def colorizeSet(z: Complex, c: Complex): Int = {
    if (brot(z, c, Iter).module() < 2) {
      return 0 // Black
    } else {
      return 16777215 // White
    }
  }

  @tailrec def render(z: Complex, c: Complex, i: Int, j: Int, matrix: BufferedImage): Option[BufferedImage] = {
    (step(MaxX, MinX, Width), step(MaxY, MinY, Height)) match {
      case (None, _) => None
      case (_, None) => None
      case (Some(deltax), Some(deltay)) =>
        (i, j) match {
          case (_, Height) => Some(matrix)
          case (Width, _) => render(z, new Complex(MinX, c.imaginary() - deltay), 0, j + 1, matrix)
          case _ =>
            matrix.setRGB(i, j, colorizeSet(z, c))
            render(z, new Complex(c.real() + deltax, c.imaginary()), i + 1, j, matrix)
        }
    }
    
  }

  def main(args: Array[String]): Unit = {
      render(new Complex(0, 0), new Complex(MinX, MaxY), 0, 0, new BufferedImage(Width, Height, BufferedImage.TYPE_BYTE_GRAY)) match {
        case None => println("Error while rendering. Verify x-y ranges and image resolution.")
        case Some(rendered) => ImageIO.write(rendered, "bmp", new File("./brot.bmp"))
      }
  }
}