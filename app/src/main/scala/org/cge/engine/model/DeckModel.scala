package org.cge.engine.model

trait DeckModel:
  def cards: List[CardModel]
  def addCard(card: CardModel): Unit
  def removeCards(numberOfCards: Int): List[CardModel]

object DeckModel:
  def apply(): DeckModel = SimpleDeck()

  class SimpleDeck() extends DeckModel:
    private var _cards: List[CardModel] = List.empty

    def cards: List[CardModel] = _cards
    def addCard(card: CardModel): Unit =
      _cards = _cards :+ card

    def removeCards(numberOfCards: Int): List[CardModel] =
      val ret: List[CardModel] = _cards.take(numberOfCards)
      _cards = _cards.drop(numberOfCards)
      ret
