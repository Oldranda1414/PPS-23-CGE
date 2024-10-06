package org.cge.engine.data

import org.cge.engine.model.GameModel.SimpleCard

/** A standard deck of french cards. */
object StandardDeck:
  val suits: List[String] = List("Hearts", "Diamonds", "Clubs", "Spades")
  val values: List[String] =
    List("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")
  val cards: List[SimpleCard] = for
    suit <- suits
    value <- values
  yield SimpleCard(value, suit)
