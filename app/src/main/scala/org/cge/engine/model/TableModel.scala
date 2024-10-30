package org.cge.engine.model

import org.cge.engine.model.TableModel.HandRule

trait TableModel:
  val deck: DeckModel
  def trump_=(suit: Suit): Unit
  def trump: Option[Suit]
  def cardsOnTable: List[CardModel]
  def playCard(card: CardModel): Unit
  def takeCards(): List[CardModel] 
  def addHandRule(rule: HandRule): Unit
  def handRules: List[HandRule]
  def doesCardWinHand(card: CardModel): Boolean

object TableModel:
  def apply(): TableModel = TableWithRules()

  abstract class SimpleTable() extends TableModel:
    val deck: DeckModel = DeckModel()
    private val tableDeck: DeckModel = DeckModel()

    private var decidedTrump: Option[Suit] = None
    def trump_=(suit: Suit) = decidedTrump = Some(suit)
    def trump = decidedTrump
    def cardsOnTable: List[CardModel] = tableDeck.cards
    def playCard(card: CardModel) = tableDeck.addCard(card)
    def takeCards(): List[CardModel] =
      tableDeck.removeCards(tableDeck.cards.size)

  type HandRule = (List[CardModel], CardModel, Option[Suit], List[Rank]) => Boolean

  class TableWithRules() extends SimpleTable:
    var handRules: List[HandRule] = List[HandRule]()
    def addHandRule(rule: HandRule): Unit = handRules = handRules :+ rule

    def doesCardWinHand(card: CardModel): Boolean =
      cardsOnTable.contains(card) &&
      handRules
        .map[Boolean](r =>
          r(super.cardsOnTable, card, super.trump, deck.cards.map(_.rank).distinct)).forall(identity)
