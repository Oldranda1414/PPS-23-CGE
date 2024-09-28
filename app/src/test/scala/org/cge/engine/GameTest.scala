package org.cge.engine

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._

class CardTest extends AnyTest:
  private val card: Card = SimpleCard("value", "suit")

  test("SimpleCard value should be \"value\""):
    card.value should be ("value")

  test("SimpleCard suit should be \"suit\""):
    card.suit should be ("suit")
