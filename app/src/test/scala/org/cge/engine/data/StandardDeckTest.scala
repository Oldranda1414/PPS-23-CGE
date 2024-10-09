package org.cge.engine.data

import org.cge.AnyTest
import org.cge.engine.model.CardModel
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class StandardDeckTest extends AnyTest with BeforeAndAfterEach:
  
  test("StandardDeck should have four suits"):
    StandardDeck.suits should be (List("Hearts", "Diamonds", "Clubs", "Spades"))

  test("StandardDeck should have thirteen values"):
    StandardDeck.values should be (List("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"))

  test("StandardDeck should have 52 cards"):
    StandardDeck.cards.size should be (52)

  test("StandardDeck should contain all combinations of suits and values"):
    val expectedCards = for
      suit <- StandardDeck.suits
      value <- StandardDeck.values
    yield CardModel(value, suit)
    StandardDeck.cards should be (expectedCards)
