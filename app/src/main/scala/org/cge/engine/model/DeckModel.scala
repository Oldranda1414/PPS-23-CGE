package org.cge.engine.model

trait DeckModel:
  def cards: List[CardModel]
  def addCard(card: CardModel): Unit
  def removeCards(numberOfCards: Int): List[CardModel]
  def getHighestCard(cards: Iterable[CardModel]): CardModel
  def removeCard(card: CardModel): Unit

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

    def removeCard(card: CardModel) =
      if !cards.contains(card) then throw new IllegalArgumentException("Card not in deck")
      cards = cards.filterNot(_ == card)

    def getHighestCard(cards: Iterable[CardModel]): CardModel =
      if cards.exists(!this.cards.contains(_)) then throw new IllegalArgumentException("Card not in deck")
      cards.maxBy(c => this.cards.indexOf(c))

