package patterns.typeclasses.examples.printable

import PrintableInstances._

object Demo extends App {

  Printable.print(10)
  Printable.print("This is a demo")

  // The following it's not possible: there's not Type Class Instance
  //Printable.print(10L)

  // After adding a new case class Person and the Type Class Instance,
  // I can do the following:
  import PersonUtils._
  val john = Person("John",30)
  Printable.print(john)
}
