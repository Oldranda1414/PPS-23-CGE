package org.cge.engine

object Game:
  trait Card:
    def value: String
    def suit: String

  trait Player:
    def name: String
    def cards: Set[Card]

  final case class SimpleCard(val value: String, val suit: String) extends Card

  final case class SimplePlayer(val name: String) extends Player:
    private var _cards: Set[Card] = Set.empty

    def cards: Set[Card] = _cards
