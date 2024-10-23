package org.cge.engine.model

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.model.TableModel.PlayingRule

class TableModelTest extends AnyTest with BeforeAndAfterEach:
  var table: TableModel = TableModel()
  val card1: CardModel = CardModel("Ace", "Spades")
  val card2: CardModel = CardModel("2", "Spades")
  val card3: CardModel = CardModel("3", "Clubs")

  // This rule is respected if on the table there are only cards of spades
  val rule: PlayingRule = (cardsOnTable: List[CardModel], card: CardModel) =>
    cardsOnTable.filterNot(card => card.suit == Suit("Spades")).size == 0
      && card.suit == Suit("Spades")

  override def beforeEach(): Unit =
    table = TableModel()

  test("TableModel cardsOnTable should be initially empty"):
    table.cardsOnTable should be (List[CardModel]())

  test("TableModel cardsOnTable should be non-empty after playing a card"):
    table.playCard(card1)
    table.cardsOnTable should not be (List[CardModel]())

  test("TableModel cardsOnTable should contain the played card after playing a card"):
    table.playCard(card1)
    table.cardsOnTable should be (List(card1))

  test("TableModel cardsOnTable should be empty after taking all the cards"):
    table.playCard(card1)
    table.playCard(card2)
    val _: List[CardModel] = table.takeCards()
    table.cardsOnTable should be (List[CardModel]())

  test("TableModel takeCards should return all the played cards"):
    table.playCard(card1)
    table.playCard(card2)
    val takenCards: List[CardModel] = table.takeCards()
    takenCards should be (List(card1, card2))

  test("TableModel takeCards should return an empty list if no card has been played"):
    val takenCards: List[CardModel] = table.takeCards()
    takenCards should be (List[CardModel]())

  test("In TableModel is possible to add playing rules"):
    table.addPlayingRule(rule)
    table.rules should be (List(rule))

  test("TableModel should mark a playable card as playable"):
    table.addPlayingRule(rule)
    table.canPlayCard(card2) should be (true)

  test("TableModel should mark a non-playable card as non-playable"):
    table.addPlayingRule(rule)
    table.canPlayCard(card3) should be (false)

  test("TableModel sholud accept playing cards following the added rules"):
    table.addPlayingRule(rule)
    table.playCard(card1)
    table.playCard(card2)
    table.cardsOnTable should be (List(card1, card2))

  test("TableModel sholud reject playing cards not following the added rules"):
    table.addPlayingRule(rule)
    table.playCard(card1)
    table.playCard(card3)
    table.cardsOnTable should be (List(card1))
