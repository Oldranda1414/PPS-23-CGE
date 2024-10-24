package org.cge.engine.model

trait DeckModel:
  def cards: List[CardModel]
  def addCard(card: CardModel): Unit
  def removeCards(numberOfCards: Int): List[CardModel]

object DeckModel:
  def apply(): DeckModel = SimpleDeck()

  class SimpleDeck() extends DeckModel:
    var cards: List[CardModel] = List.empty

    def addCard(card: CardModel): Unit =
      cards = cards :+ card

    def removeCards(numberOfCards: Int): List[CardModel] =
      val ret: List[CardModel] = cards.take(numberOfCards)
      cards = cards.drop(numberOfCards)
      ret
