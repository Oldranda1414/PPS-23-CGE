package org.cge.engine.data

import org.cge.engine.model._

/** A standard deck of french cards. */
object StandardDeck:
  val suits: Set[Suit] = Set(Clubs, Diamonds, Hearts, Spades)
  val ranks: List[Rank] = Rank.standardRanks
  val cards: List[CardModel] = for
    suit <- suits.toList
    rank <- ranks
  yield CardModel(rank, suit)
