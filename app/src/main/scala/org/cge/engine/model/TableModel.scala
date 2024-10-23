package org.cge.engine.model

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
    override def playCard(card: CardModel) =
      _rules.map[Boolean](r => r(super.cardsOnTable, card)).forall(identity) match
        case true => super.playCard(card)
        case false => _rules = _rules
