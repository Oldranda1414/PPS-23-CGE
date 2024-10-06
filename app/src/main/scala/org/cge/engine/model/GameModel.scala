package org.cge.engine.model

import org.cge.engine.model.GameModel.PlayerModel

trait GameModel:
  def players: List[PlayerModel]
  def addPlayer(player: PlayerModel): Unit
  def removePlayer(player: PlayerModel): Unit
  val name: String

object GameModel:

  trait CardModel:
    def value: String
    def suit: String

  trait PlayerModel:
    def name: String
    def deck: DeckModel

  trait DeckModel:
    def cards: List[CardModel]
    def addCard(card: CardModel): Unit
    def drawCards(numberOfCards: Int): List[CardModel]

  final case class SimpleGame(val name: String) extends GameModel:
    private var _players: List[PlayerModel] = List.empty

    def players: List[PlayerModel] = _players
    def addPlayer(player: PlayerModel): Unit =
      _players = _players :+ player
    def removePlayer(player: PlayerModel): Unit =
      _players = _players.filterNot(_ == player)

  final case class SimpleCard(val value: String, val suit: String) extends CardModel

  final case class SimplePlayer(val name: String) extends PlayerModel:
    val deck: DeckModel = SimpleDeck()

  final case class SimpleDeck() extends DeckModel:
    private var _cards: List[CardModel] = List.empty

    def cards: List[CardModel] = _cards
    def addCard(card: CardModel): Unit =
      _cards = _cards :+ card

    def drawCards(numberOfCards: Int): List[CardModel] =
      val ret: List[CardModel] = _cards.take(numberOfCards)
      _cards = _cards.drop(numberOfCards)
      ret
