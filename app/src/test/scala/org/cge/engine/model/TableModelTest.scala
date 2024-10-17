package org.cge.engine.model

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

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
