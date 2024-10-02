package org.cge.engine

object Game:
  trait Card:
    def value: String
    def suit: String

  trait Player:
    def name: String
    def cards: Set[Card]
    def addCard(card: Card): Unit

  trait Deck:
    def cards: List[Card]
    def addCard(card: Card): Unit

  final case class SimpleCard(val value: String, val suit: String) extends Card

  final case class SimplePlayer(val name: String) extends Player:
    private var _cards: Set[Card] = Set.empty

    def cards: Set[Card] = _cards
    def addCard(card: Card) = _cards += card

  final case class SimpleDeck() extends Deck:
    private var _cards: List[Card] = List.empty

    def cards: List[Card] = _cards
    def addCard(card: Card): Unit =
      _cards = card :: _cards
