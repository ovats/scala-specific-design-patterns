package patterns.typeclasses.examples.printable

import PrintableInstances._

object Demo extends App {

  Printable.print(10)
  Printable.print("This is a demo")

  // The following it's not possible: there's not Type Class Instance
  //Printable.print(10L)

}
