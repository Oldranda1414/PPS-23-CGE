package org.cge.dsl

import org.cge.AnyTest
import org.cge.dsl.GameBuilder.DSL.Game
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class GameDslTest extends AnyTest with BeforeAndAfterEach:

  override def beforeEach(): Unit =
    Game.reset()

  test("is method can assign name to the game"):
    val builder = Game is "Simple Game"
    val game = builder.build
    game.name shouldBe "Simple Game"

  test("is method cannot assign the name twice"):
    Game is "Simple Game"
    val exception = intercept[IllegalArgumentException](Game is "Another Game")
    exception shouldBe a[IllegalArgumentException]

  test("is method cannot assign an empty name"):
    val exception = intercept[IllegalArgumentException]:
      Game is ""
    exception shouldBe a[IllegalArgumentException]

  test("is method cannot assign a blank name"):
    val exception = intercept[IllegalArgumentException]:
      Game is "   "
    exception shouldBe a[IllegalArgumentException]
