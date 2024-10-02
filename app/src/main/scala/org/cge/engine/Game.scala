package org.cge.engine

object Game:
  trait Card:
    def value: String
    def suit: String

  trait Player:
    def name: String
    def deck: Deck

  trait Deck:
    def cards: List[Card]
    def addCard(card: Card): Unit
    def drawCards(numberOfCards: Int): List[Card]

  final case class SimpleCard(val value: String, val suit: String) extends Card

  final case class SimplePlayer(val name: String) extends Player:
    val deck: Deck = SimpleDeck()

  final case class SimpleDeck() extends Deck:
    private var _cards: List[Card] = List.empty

    def cards: List[Card] = _cards
    def addCard(card: Card): Unit =
      _cards = _cards :+ card
    
    def drawCards(numberOfCards: Int): List[Card] =
      val ret: List[Card] = _cards.take(numberOfCards)
      _cards = _cards.drop(numberOfCards)
      ret
