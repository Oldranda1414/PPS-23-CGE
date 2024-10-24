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

    def canPlayCard(card: CardModel): Boolean =
      _rules.map[Boolean](r => r(super.cardsOnTable, card)).forall(identity)

    override def playCard(card: CardModel) =
      canPlayCard(card) match
        case true => super.playCard(card)
        case false => ()
