package org.cge.engine.view

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class StateTest extends AnyTest with BeforeAndAfterEach:
  import org.cge.engine.view.monads.States._
  import org.cge.engine.view.monads.Monads.*

  private var state: State[Int, String] = State(s => (s + 1, s"state: $s"))

  override def beforeEach(): Unit =
    state = State(s => (s + 1, s"state: $s"))

  test("State should transform the state and return the expected value"):
    val (newState, result) = state(0)
    newState should be (1)
    result should be ("state: 0")

  test("StateMonad unit should create a state that returns the given value"):
    val unitState = summon[Monad[[A] =>> State[Int, A]]].unit("test")
    val (newState, result) = unitState(0)
    newState should be (0)
    result should be ("test")

  test("StateMonad flatMap should chain state transformations"):
    val chainedState = state.flatMap(s => State(s2 => (s2 + 2, s"chained: $s2")))
    val (newState, result) = chainedState(0)
    newState should be (3)
    result should be ("chained: 1")
