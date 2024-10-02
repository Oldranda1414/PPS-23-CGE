package org.cge.engine

import scala.collection.mutable.Set
import org.cge.AnyTest
import org.cge.engine.Game._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class CardTest extends AnyTest with BeforeAndAfterEach:
  private var card: Card = SimpleCard("value", "suit")

  override def beforeEach(): Unit =
    card = SimpleCard("value", "suit")

  test("SimpleCard value should be \"value\""):
    card.value should be ("value")

  test("SimpleCard suit should be \"suit\""):
    card.suit should be ("suit")

class PlayerTest extends AnyTest with BeforeAndAfterEach:
  private var player: Player = SimplePlayer("name")
  private var card: Card = SimpleCard("1", "Spades")

  override def beforeEach(): Unit =
    player = SimplePlayer("name")
    card = SimpleCard("1", "Spades")

  test("SimplePlayer name should be \"name\""):
    player.name should be ("name")

  test("SimplePlayer cards set should be empty after initialization"):
    player.cards should be (Set[Card]())
  
  test("SimplePlayer cards set should be non-empty after addCard"):
    player.addCard(card)
    player.cards should not be (Set[Card]())

  test("SimplePlayer cards set should contain the added card after addCard"):
    player.addCard(card)
    player.cards should contain (card)

class DeckTest extends AnyTest with BeforeAndAfterEach:
  private var deck: Deck = SimpleDeck()
  private val cards: List[Card] =
      Range(1, 10)
      .toList
      .map(e => SimpleCard(e.toString, "Spades"))
  private val card: Card = cards.head
  private val numberOfDrawnCards: Int = 3

  override def beforeEach(): Unit =
    deck = SimpleDeck()

  test("SimpleDeck should be non-empty after addCard"):
    deck.addCard(card)
    deck.cards should not be (List[Card]())

  test("SimpleDeck should contain the added card"):
    deck.addCard(card)
    deck.cards should contain (card)
  
  test("SimpleDeck should contain multiple added cards in order"):
    cards.foreach(deck.addCard(_))
    deck.cards should be (cards)
  
  test("Drawing from a SimpleDeck returns the first N cards of the deck"):
    cards.foreach(deck.addCard(_))
    val drawnCards = deck.drawCards(numberOfDrawnCards)
    drawnCards should be (cards.take(numberOfDrawnCards))

  test("Drawing from a SimpleDeck removes the first N cards of the deck"):
    cards.foreach(deck.addCard(_))
    val drawnCards = deck.drawCards(numberOfDrawnCards)
    (drawnCards ++ deck.cards) should be (cards)
