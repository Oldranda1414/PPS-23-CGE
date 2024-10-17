package org.cge.engine.model

trait GameModel:
  def players: List[PlayerModel]
  def addPlayer(player: PlayerModel): Unit
  def removePlayer(player: PlayerModel): Unit
  val name: String

object GameModel:
  def apply(name: String, withTable: Boolean = false): GameModel = withTable match
    case false => SimpleGame(name)
    case true => TableGame(name)

  class SimpleGame(val name: String) extends GameModel:
    private var _players: List[PlayerModel] = List.empty

    def players: List[PlayerModel] = _players
    def addPlayer(player: PlayerModel): Unit =
      _players = _players :+ player
    def removePlayer(player: PlayerModel): Unit =
      _players = _players.filterNot(_ == player)
  
  class TableGame(name: String) extends SimpleGame(name):
    val table: TableModel = TableModel()

trait CardModel:
  def value: String
  def suit: String

object CardModel:
  def apply(value: String, suit: String): CardModel = SimpleCard(value, suit)
  
  final case class SimpleCard(val value: String, val suit: String) extends CardModel

trait PlayerModel:
  def name: String
  def hand: DeckModel

object PlayerModel:
  def apply(name: String): PlayerModel = SimplePlayer(name)

  final case class SimplePlayer(val name: String) extends PlayerModel:
    val hand: DeckModel = DeckModel()

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

trait TableModel:
  def cardsOnTable: DeckModel

object TableModel:
  def apply(deck: DeckModel = DeckModel()): TableModel = SimpleTable(deck)

  final case class SimpleTable(val cardsOnTable: DeckModel) extends TableModel
