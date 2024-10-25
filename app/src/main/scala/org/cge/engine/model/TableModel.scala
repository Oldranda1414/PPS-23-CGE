package org.cge.engine.model

import org.cge.engine.model.TableModel.PlayingRule

trait TableModel:
  def cardsOnTable: List[CardModel]
  def playCard(card: CardModel): Unit
  def takeCards(): List[CardModel] 
  def addPlayingRule(rule: PlayingRule): Unit
  def rules: List[PlayingRule]
  def canPlayCard(card: CardModel): Boolean

object TableModel:
  def apply(): TableModel = TableWithRules()

  abstract class SimpleTable() extends TableModel:
    private val tableDeck: DeckModel = DeckModel()

    def cardsOnTable: List[CardModel] = tableDeck.cards
    def playCard(card: CardModel) = tableDeck.addCard(card)
    def takeCards(): List[CardModel] =
      tableDeck.removeCards(tableDeck.cards.size)

  type PlayingRule = (List[CardModel], CardModel) => Boolean

  class TableWithRules() extends SimpleTable:
    var rules: List[PlayingRule] = List[PlayingRule]()

    def addPlayingRule(rule: PlayingRule): Unit = rules = rules :+ rule

    def canPlayCard(card: CardModel): Boolean =
      rules.map[Boolean](r => r(super.cardsOnTable, card)).forall(identity)

    override def playCard(card: CardModel) =
      canPlayCard(card) match
        case true => super.playCard(card)
        case false => rules = rules
