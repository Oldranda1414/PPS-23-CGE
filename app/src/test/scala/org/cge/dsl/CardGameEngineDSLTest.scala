package org.cge.dsl

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.cge.engine.GameBuilder
import org.cge.dsl.CardGameEngineDSL._

class CardGameEngineDSLTest extends AnyTest:        

  test("Game word should retrieve a GameBuilder"):
    val g = game
    g shouldBe a[GameBuilder]
