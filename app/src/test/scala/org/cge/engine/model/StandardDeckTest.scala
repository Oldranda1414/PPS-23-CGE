package org.cge.engine.model

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.model._

class StandardDeckTest extends AnyTest with BeforeAndAfterEach:
  
  test("StandardDeck should have four suits"):
    StandardDeck.suits.size should be (4)

  test("StandardDeck should have thirteen ranks"):
    StandardDeck.ranks.size should be (13)

  test("StandardDeck should have 52 cards"):
    StandardDeck.cards.size should be (52)

  test("StandardDeck should contain all combinations of suits and rank"):
    val expectedCards = for
      suit <- StandardDeck.suits
      rank <- StandardDeck.ranks
    yield CardModel(rank, suit)
    StandardDeck.cards should contain theSameElementsAs expectedCards
