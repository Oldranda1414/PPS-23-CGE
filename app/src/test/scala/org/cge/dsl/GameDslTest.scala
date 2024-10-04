package org.cge.dsl

import org.cge.AnyTest
import org.cge.dsl.GameBuilder.DSL.Game
import org.scalatest.matchers.should.Matchers._

class GameDslTest extends AnyTest:
  test("is method can assign name to the game"):
    Game is "Simple Game"
    val game = Game.build
    game.name shouldBe "Simple Game"

    