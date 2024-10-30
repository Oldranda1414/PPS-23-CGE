package org.cge.engine.model

trait PlayerModel:
  def name: String
  def hand: DeckModel
  def playedCards: DeckModel
  def playCard(card: CardModel): Unit
  def points: Int
  def points_=(value: Int): Unit

object PlayerModel:
  def apply(name: String): PlayerModel = SimplePlayer(name)

  final case class SimplePlayer(val name: String) extends PlayerModel:
    val hand: DeckModel = DeckModel()
    val playedCards: DeckModel = DeckModel()

    private var playerPoints: Int = 0

    def points = playerPoints
    def points_=(value: Int): Unit = 
      require(value > 0, "Points must be greater than 0")
      playerPoints = value

    def playCard(card: CardModel): Unit = 
      require(hand.cards.contains(card), "Card must be in hand")
      hand.removeCard(card)
      playedCards.addCard(card)
