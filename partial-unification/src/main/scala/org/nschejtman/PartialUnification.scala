package org.nschejtman

object PartialUnification {
  def main(args: Array[String]): Unit = {
    // The `Option` type constructor can construct types like `Option[Int]` & `Option[String]`
    val intOption: Option[Int]       = Some(1)
    val stringOption: Option[String] = Some("Hello world")

    // Define a type constructor `MyType` which takes 1 type parameter `A`
    class MyType[A]

    // Define a types that take type constructor parameters
    import scala.language.higherKinds
    class AnotherType[F[_]]       // type constructor parameter `F` of arity 1
    class YetAnotherType[F[_, _]] // type constructor parameter `F` of arity 2

    new AnotherType[Option]
    new YetAnotherType[Map]

    // Type constructor & type inference
    def foo[F[_], A](fa: F[A]): String = fa.toString
    foo { x: Int =>
      x.toDouble
    } // compiles because of "-Ypartial-unification" enabled in build.sbt

    // Right bias
    def transform[A, B, F[_]](fa: F[A], func: A => B): F[B] = ???

    val map: Map[String, String] = Map {
      "One"   -> "One"
      "Two"   -> "Two"
      "Three" -> "Three"
    }

    val toInt = (i: String) =>
      i match {
        case "One"   => 1
        case "Two"   => 2
        case "Three" => 3
        case _       => throw new IllegalArgumentException("unknown number")
    }

    val res: Map[String, Int] = transform(map, toInt) // notice we obtain a Map[String, Int], the inverse Map[Int, String] does not compile

  }
}
