package org.cge.engine.model

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.model.TableModel.PlayingRule
import org.cge.engine.model.TableModel.HandRule

class TableModelTest extends AnyTest with BeforeAndAfterEach:
  var table: TableModel = TableModel()
  val card1: CardModel = CardModel("Ace", "Spades")
  val card2: CardModel = CardModel("2", "Spades")
  val card3: CardModel = CardModel("3", "Clubs")

  // This rule is respected if on the table there are only cards of spades
  val playingRule: PlayingRule = (table: TableModel, card: CardModel) =>
    table.cardsOnTable.filterNot(card => card.suit == Suit("Spades")).size == 0
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

  test("In TableModel it is possible to add playing rules"):
    table.addPlayingRule(playingRule)
    table.playingRules should be (List(playingRule))

  test("TableModel should mark a playable card as playable"):
    table.addPlayingRule(playingRule)
    table.canPlayCard(card2) should be (true)

  test("TableModel should mark a non-playable card as non-playable"):
    table.addPlayingRule(playingRule)
    table.canPlayCard(card3) should be (false)

  test("TableModel sholud accept playing cards following the added rules"):
    table.addPlayingRule(playingRule)
    table.playCard(card1)
    table.playCard(card2)
    table.cardsOnTable should be (List(card1, card2))

  test("TableModel sholud reject playing cards not following the added rules"):
    table.addPlayingRule(playingRule)
    table.playCard(card1)
    table.playCard(card3)
    table.cardsOnTable should be (List(card1))

  test("In TableModel it is possible to add a hand rule"):
    val handRule: HandRule = (cardsOnTable, card, trump) =>
      true || cardsOnTable == card || card == trump
    table.addHandRule(handRule)
    table.handRules should be (List(handRule))

  test("TableModel should tell what card on table determines the winning of the current hand"):
    val handRule: HandRule = (cardsOnTable, card, trump) =>
      card.suit == trump
    table.trump = "Spades"
    table.addHandRule(handRule)
    table.playCard(card1)
    table.playCard(card3)
    table.doesCardWinHand(card1) should be (true)
    table.doesCardWinHand(card3) should be (false)

  test("TableModel doesCardWinHand should return false if the card is not in the current hand"):
    val handRule: HandRule = (cardsOnTable, card, trump) =>
      card.suit == trump
    table.trump = "Spades"
    table.addHandRule(handRule)
    table.playCard(card1)
    table.playCard(card3)
    table.doesCardWinHand(card2) should be (false)
