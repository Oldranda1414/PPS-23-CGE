package org.cge.engine.model

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.model.TableModel.TableWithRules
import org.cge.engine.model.TableModel.PlayingRule

class TableModelTest extends AnyTest with BeforeAndAfterEach:
  var table: TableModel = TableModel()
  val card1: CardModel = CardModel(Ace, Spades)
  val card2: CardModel = CardModel(Two, Spades)

  override def beforeEach(): Unit =
    table = TableModel()

  test("SimpleTable cardsOnTable should be initially empty"):
    table.cardsOnTable should be (List[CardModel]())

  test("SimpleTable cardsOnTable should be non-empty after playing a card"):
    table.playCard(card1)
    table.cardsOnTable should not be (List[CardModel]())

  test("SimpleTable cardsOnTable should contain the played card after playing a card"):
    table.playCard(card1)
    table.cardsOnTable should be (List(card1))

  test("SimpleTable cardsOnTable should be empty after taking all the cards"):
    table.playCard(card1)
    table.playCard(card2)
    val _: List[CardModel] = table.takeCards()
    table.cardsOnTable should be (List[CardModel]())

  test("SimpleTable takeCards should return all the played cards"):
    table.playCard(card1)
    table.playCard(card2)
    val takenCards: List[CardModel] = table.takeCards()
    takenCards should be (List(card1, card2))

  test("SimpleTable takeCards should return an empty list if no card has been played"):
    val takenCards: List[CardModel] = table.takeCards()
    takenCards should be (List[CardModel]())

class TableWithRulesTest extends AnyTest with BeforeAndAfterEach:
  var table: TableModel = TableModel(true)
  // This rule is respected if on the table there are only cards of spades
  val rule: PlayingRule = (cardsOnTable: List[CardModel], card: CardModel) =>
    cardsOnTable.filterNot(card => card.suit == Spades).size == 0
      && card.suit == Spades

  override def beforeEach(): Unit =
    table = TableModel(true)

  test("With TableWithRules is possible to add playing rules"):
    val rule: PlayingRule = (_: List[CardModel], _: CardModel) => true

    table match
      case t: TableWithRules =>
        t.addPlayingRule(rule)
        t.rules should be (List(rule))
      case _ => fail("TableModel was not a TableWithRules, check apply() function.")

  test("TableWithRules sholud accept playing cards following the added rules"):
    table match
      case t: TableWithRules =>
        t.addPlayingRule(rule)
        t.playCard(CardModel(Ace, Spades))
        t.playCard(CardModel(Two, Spades))
        t.cardsOnTable should be (List(CardModel(Ace, Spades), CardModel(Two, Spades)))
      case _ => fail("TableModel was not a TableWithRules, check apply() function.")

  test("TableWithRules sholud reject playing cards not following the added rules"):
    table match
      case t: TableWithRules =>
        t.addPlayingRule(rule)
        t.playCard(CardModel(Ace, Spades))
        t.playCard(CardModel(Two, Clubs))
        t.cardsOnTable should be (List(CardModel(Ace, Spades)))
      case _ => fail("TableModel was not a TableWithRules, check apply() function.")
