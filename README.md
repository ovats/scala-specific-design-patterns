# Scala Specific Design Patterns

List of design patterns in this project:

- Lazy Parameters (to do)
- Type Classes
- Stackable Traits (to do)
- Cake Pattern (to do)

## Type Classes

There are three important components to the type class pattern:
 
- the Type Class itself, 
- instances for particular types, 
- and the interface methods that we expose to users.

The Type Class is an interface or API that represents some functionality we want to implement, is represented by a trait with at least one type parameter.

```scala
trait JsonWriter[A] {
    def write(value: A): Json
}
```
         
JsonWriter is our type class in this example, with Json and its subtypes providing supporting code.

Type Class Instances provide implementations for the types we care about, including types from the Scala standard library and types from our domain model.

```scala
final case class Person(name: String, email: String)

object JsonWriterInstances {

  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      def write(value: String): Json = JsString(value)
    }

  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      def write(value: Person): Json =
        JsObject(Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
        ))
    }

  // etc...
}
```

A type class interface is any functionality we expose to users. Interfaces are generic methods that accept instances of the type class as implicit parameters.
There are two common ways of specifying an interface: Interface Objects and Interface Syntax.

Interface Objects

The simplest way of creating an interface is to place methods in a singleton
object:

```scala
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}
```

To use this object, we import any type class instances we care about and call
the relevant method:

```scala
import JsonWriterInstances._

Json.toJson(Person("Dave", "dave@example.com"))
// res4: Json = JsObject(Map(name -> JsString(Dave), email -> JsString
  (dave@example.com)))
```

Interface Syntax

We can alternatively use extension methods to extend exiting types with interface methods². Cats refers to this as “syntax” for the type class:

```scala
object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json =
      w.write(value)
  }
}
```

We use interface syntax by imporঞng it alongside the instances for the types we need:

```scala
import JsonWriterInstances._
import JsonSyntax._

Person("Dave", "dave@example.com").toJson
// res6: Json = JsObject(Map(name -> JsString(Dave), email -> JsString
    (dave@example.com)))
```

### Example 1: Printable Library

Let’s define a Printable type class to work around these problems:

- Define a type class Printable[A] containing a single method format. format should accept a value of type A and return a String.
- Create an object PrintableInstances containing instances of Printable for String and Int.
- Define an object Printable with two generic interface methods:
    * format accepts a value of type A and a Printable of the corresponding type. It uses the relevant Printable to convert the A to a String.
    * print accepts the same parameters as format and returns Unit. It prints the A value to the console using println.
                                                                                              
File `Printable.scala` contains:

- Type Class: `Trait Printable[A]`
- Type Class instances in `object PrintableInstances`
- Type Class interface in `object Printable`. This is the `Interface Objects` solution.

In `object PrintableInstances` we have definitions for `Int` and `String`.
What if we want use the `Printable[A]` for other objects?

We have defined a case class `Person` and its type class instance in the `Person.scala` file.
                                                                                              
