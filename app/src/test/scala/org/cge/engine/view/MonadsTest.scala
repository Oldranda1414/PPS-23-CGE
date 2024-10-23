package org.cge.engine.view

import org.cge.AnyTest
import org.cge.engine.view.monads.Streams.*
import org.cge.engine.view.monads.Monads.*
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

given optionMonad: Monad[Option] with
  def unit[A](a: A): Option[A] = Some(a)
  extension [A](m: Option[A])
    def flatMap[B](f: A => Option[B]): Option[B] = m match
      case Some(value) => f(value)
      case None        => None

class OptionMonadTest extends AnyTest with BeforeAndAfterEach:

  val option: Option[Int] = Some(10)

  test("Monad unit method should wrap value in monad"):
    val wrappedValue = summon[Monad[Option]].unit(42)
    wrappedValue should be(Some(42))

  test("flatMap should apply function to the monadic value"):
    val result = option.flatMap(x => Some(x * 2))
    result should be(Some(20))

  test("map should transform the monadic value"):
    val result = option.map(_ + 3)
    result should be(Some(13))

class MonadOperationsTest extends AnyTest with BeforeAndAfterEach:

  val m1: Option[Int] = Some(2)
  val m2: Option[Int] = Some(3)

  test("map2 should combine two monads"):
    val result = Monad.map2(m1, m2)(_ + _)
    result should be(Some(5))

  test("seq should return the second monadic value"):
    val result = Monad.seq(m1, m2)
    result should be(Some(3))

class StreamMonadTest extends AnyTest with BeforeAndAfterEach:

  val stream: Stream[Option[Int]] =
    Stream.Cons(() => Some(1), () => Stream.Cons(() => Some(2), () => Stream.Cons(() => Some(3), () => Stream.Empty())))

  test("seqN should process a stream of monads sequentially"):
    val result = Monad.seqN(stream)
    result should be(Some(3))
