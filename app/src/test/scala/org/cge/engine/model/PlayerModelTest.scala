package org.cge.engine.model

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class PlayerModelTest extends AnyTest with BeforeAndAfterEach:
  private var player: PlayerModel = PlayerModel("name")
  private val card: CardModel = CardModel("Ace", "Spades")

  override def beforeEach(): Unit =
    player = PlayerModel("name")

  test("SimplePlayer name should be \"name\""):
    player.name should be ("name")

  test("SimplePlayer cards set should be empty after initialization"):
    player.hand.cards should be (List[CardModel]())
  
  test("SimplePlayer cards set should be non-empty after addCard"):
    player.hand.addCard(card)
    player.hand.cards should not be (List[CardModel]())

  test("SimplePlayer cards set should contain the added card after addCard"):
    player.hand.addCard(card)
    player.hand.cards should contain (card)

