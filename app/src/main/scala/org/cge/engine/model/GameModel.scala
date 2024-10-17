package org.cge.engine.model

import org.cge.engine._

trait GameModel:
  def players: List[PlayerModel]
  def addPlayer(player: PlayerModel): Unit
  def removePlayer(player: PlayerModel): Unit
  val name: String

object GameModel:
  def apply(name: String, withTable: Boolean = false): GameModel =
    withTable match
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
  def rank: Rank
  def suit: Suit

object CardModel:
  def apply(rank: Rank, suit: Suit): CardModel = SimpleCard(rank, suit)
  
  final case class SimpleCard(val rank: Rank, val suit: Suit) extends CardModel

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
  def cardsOnTable: List[CardModel]
  def playCard(card: CardModel): Unit
  def takeCards(): List[CardModel] 

object TableModel:
  def apply(withRules: Boolean = false): TableModel = withRules match
    case false => SimpleTable()
    case true => TableWithRules()

  class SimpleTable() extends TableModel:
    private val _cardsOnTable: DeckModel = DeckModel()

    def cardsOnTable: List[CardModel] = _cardsOnTable.cards
    def playCard(card: CardModel) = _cardsOnTable.addCard(card)
    def takeCards(): List[CardModel] =
      _cardsOnTable.removeCards(_cardsOnTable.cards.size)

  type PlayingRule = (List[CardModel], CardModel) => Boolean
  class TableWithRules() extends SimpleTable:
    private var _rules: List[PlayingRule] = List[PlayingRule]()

    def addPlayingRule(rule: PlayingRule): Unit = _rules = _rules :+ rule
    def rules: List[PlayingRule] = _rules
    override def playCard(card: CardModel) = _rules.map[Boolean](r => r(super.cardsOnTable, card)).forall(identity) match
      case true => super.playCard(card)
      case false => _rules = _rules
