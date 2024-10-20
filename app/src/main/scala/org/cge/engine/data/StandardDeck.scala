package org.cge.engine.data

import org.cge.engine.model._

/** A standard deck of french cards. */
object StandardDeck:
  val suits: Set[Suit] = Set(Clubs, Diamonds, Hearts, Spades)
  val ranks: List[Rank] =
    List(Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace)
  val cards: List[CardModel] = for
    suit <- suits.toList
    rank <- ranks
  yield CardModel(rank, suit)
