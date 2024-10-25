package org.cge.engine.model

import org.cge.engine.model.TableModel.PlayingRule
import org.cge.engine.model.TableModel.HandRule

trait TableModel:
  def cardsOnTable: List[CardModel]
  def playCard(card: CardModel): Unit
  def takeCards(): List[CardModel] 
  def addPlayingRule(rule: PlayingRule): Unit
  def addHandRule(rule: HandRule): Unit
  def playingRules: List[PlayingRule]
  def handRules: List[HandRule]
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
  type HandRule = (List[CardModel], CardModel, Suit) => Boolean

  class TableWithRules() extends SimpleTable:
    var playingRules: List[PlayingRule] = List[PlayingRule]()
    var handRules: List[HandRule] = List[HandRule]()

    def addPlayingRule(rule: PlayingRule): Unit = playingRules = playingRules :+ rule
    def addHandRule(rule: HandRule): Unit = handRules = handRules :+ rule

    def canPlayCard(card: CardModel): Boolean =
      playingRules.map[Boolean](r => r(super.cardsOnTable, card)).forall(identity)

    override def playCard(card: CardModel) =
      canPlayCard(card) match
        case true => super.playCard(card)
        case false => playingRules = playingRules
