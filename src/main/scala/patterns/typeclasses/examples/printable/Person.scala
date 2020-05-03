package patterns.typeclasses.examples.printable

case class Person (name: String, age: Int)

object Person {

}

object PersonUtils {
  implicit val personPrintable: Printable[Person] = new Printable[Person] {
    override def format(value: Person): String = s"[Person] name=${value.name} age=${value.age}"
  }
}