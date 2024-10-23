package org.cge.engine.model

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class CardModelTest extends AnyTest with BeforeAndAfterEach:
  private val card: CardModel = CardModel("2", "Clubs")

  test("SimpleCard rank should be Two"):
    card.rank should be ("2")

  test("SimpleCard suit should be Clubs"):
    card.suit should be ("Clubs")
