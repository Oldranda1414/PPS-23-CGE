package org.cge.engine

import scala.collection.mutable.Set
import org.cge.AnyTest
import org.cge.engine.Game._
import org.scalatest.matchers.should.Matchers._

class CardTest extends AnyTest:
  private val card: Card = SimpleCard("value", "suit")

  test("SimpleCard value should be \"value\""):
    card.value should be ("value")

  test("SimpleCard suit should be \"suit\""):
    card.suit should be ("suit")

class PlayerTest extends AnyTest:
  private val player: Player = SimplePlayer("name")

  test("SimplePlayer name should be \"name\""):
    player.name should be ("name")

  test("SimplePlayer cards set should be empty after initialization"):
    player.cards should be (Set[Card]())
  
  test("SimplePlayer cards set must be non-empty after adding a card"):
    player.addCard(SimpleCard("1", "spades"))
    player.cards should not be (Set[Card]())

  test("SimplePlayer cards set must contain a card after adding that card"):
    val card = SimpleCard("1", "spades")
    player.addCard(card)
    player.cards should contain (card)
