package org.cge.engine.view

import org.cge.AnyTest
import org.cge.engine.view.monads.Streams.*
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*

class StreamTests extends AnyTest with BeforeAndAfterEach:

  val stream = Stream.cons(1, Stream.cons(2, Stream.cons(3, Stream.empty())))

  test("toList should convert a stream to a sequence"):
    val list = stream.toList
    list should be(Sequence.Cons(1, Sequence.Cons(2, Sequence.Cons(3, Sequence.Nil()))))

  test("map should apply a function to all elements of the stream"):
    val mappedStream = stream.map(_ * 2)
    mappedStream.toList should be(Sequence.Cons(2, Sequence.Cons(4, Sequence.Cons(6, Sequence.Nil()))))

  test("filter should only keep elements that match the predicate"):
    val filteredStream = stream.filter(_ % 2 != 0)
    filteredStream.toList should be(Sequence.Cons(1, Sequence.Cons(3, Sequence.Nil())))

  test("take should return the first n elements of the stream"):
    val takenStream = stream.take(2)
    takenStream.toList should be(Sequence.Cons(1, Sequence.Cons(2, Sequence.Nil())))

  test("iterate should generate an infinite stream of repeated applications of a function"):
    val iteratedStream = Stream.iterate(1)(_ + 1).take(3)
    iteratedStream.toList should be(Sequence.Cons(1, Sequence.Cons(2, Sequence.Cons(3, Sequence.Nil()))))

  test("generate should create a stream based on a supplier function"):
    var counter = 0
    val generatedStream = Stream.generate(() => { counter += 1; counter }).take(3)
    generatedStream.toList should be(Sequence.Cons(1, Sequence.Cons(2, Sequence.Cons(3, Sequence.Nil()))))

