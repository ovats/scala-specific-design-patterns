package patterns.typeclasses.examples.printable

// The Type Class:
trait Printable[A] {
  def format(value: A): String
}

// The Type Class Instances:
object PrintableInstances {

  implicit val printableString: Printable[String] = new Printable[String]{
    override def format(value: String): String = value
  }

  implicit val printableInt: Printable[Int] = new Printable[Int] {
    override def format(value: Int): String = s"$value"
  }

}

// The Type Class Interface:
object Printable {
  def format[A](value: A)(implicit w: Printable[A]): String = w.format(value)
  def print[A](value: A)(implicit w: Printable[A]): Unit = println(w.format(value))
}

