package org.cge.engine.model

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class CardModelTest extends AnyTest with BeforeAndAfterEach:
  private val card: CardModel = CardModel(Two, Clubs)

  test("CardModel rank should be Two"):
    card.rank should be (Two)

  test("CardModel suit should be Clubs"):
    card.suit should be (Clubs)
