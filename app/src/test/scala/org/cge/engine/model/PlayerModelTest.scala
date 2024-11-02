package org.cge.engine.model

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

import scala.language.implicitConversions

class PlayerModelTest extends AnyTest with BeforeAndAfterEach:
  private var player: PlayerModel = PlayerModel("name")
  private val card: CardModel = CardModel("Ace", "Spades")

  override def beforeEach(): Unit =
    player = PlayerModel("name")

  test("PlayerModel name should be \"name\""):
    player.name should be ("name")

  test("PlayerModel cards set should be empty after initialization"):
    player.hand.cards should be (List[CardModel]())
  
  test("PlayerModel cards set should be non-empty after addCard"):
    player.hand.addCard(card)
    player.hand.cards should not be (List[CardModel]())

  test("PlayerModel cards set should contain the added card after addCard"):
    player.hand.addCard(card)
    player.hand.cards should contain (card)

  test("PlayerModel points should be 0 after initialization"):
    player.points should be (0)

  test("PlayerModel points should be 10 after setting points to 10"):
    player.points = 10
    player.points should be (10)

  test("PlayerModel points should throw IllegalArgumentException if set to -1"):
    an [IllegalArgumentException] should be thrownBy:
      player.points = -1

  test("PlayerModel should throw IllegalArgumentException if playCard is called with a card not in hand"):
    an [IllegalArgumentException] should be thrownBy:
      player.playCard(card)

  test("PlayerModel should remove card from hand after playCard"):
    player.hand.addCard(card)
    player.playCard(card)
    player.hand.cards should not contain (card)

  test("PlayerModel should add card to playedCards after playCard"):
    player.hand.addCard(card)
    player.playCard(card)
    player.playedCards.cards should contain (card)

  test("PlayerModel should have no cards played at start"):
    player.playedCards.cards should be (List[CardModel]())

  
