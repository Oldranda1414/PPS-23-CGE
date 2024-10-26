package org.cge.engine.model

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.data.StandardDeck

class DeckModelTest extends AnyTest with BeforeAndAfterEach:
  private var deck: DeckModel = DeckModel()
  private val cards: List[CardModel] = StandardDeck.ranks
    .map(r => CardModel(r, "Spades"))
  private val card: CardModel = cards.head
  private val numberOfDrawnCards: Int = 3

  override def beforeEach(): Unit =
    deck = DeckModel()

  test("DeckModel should be non-empty after addCard"):
    deck.addCard(card)
    deck.cards should not be (List[CardModel]())

  test("DeckModel should contain the added card"):
    deck.addCard(card)
    deck.cards should contain (card)
  
  test("DeckModel should contain multiple added cards in order"):
    cards.foreach(deck.addCard(_))
    deck.cards should be (cards)
  
  test("Drawing from a DeckModel returns the first N cards of the deck"):
    cards.foreach(deck.addCard(_))
    val drawnCards = deck.removeCards(numberOfDrawnCards)
    drawnCards should be (cards.take(numberOfDrawnCards))

  test("Drawing from a DeckModel removes the first N cards of the deck"):
    cards.foreach(deck.addCard(_))
    val drawnCards = deck.removeCards(numberOfDrawnCards)
    (drawnCards ++ deck.cards) should be (cards)

  test("Get highest card returns the highest card passed as argument using the deck as comparer"):
    cards.foreach(deck.addCard(_))
    val highestCard = deck.getHighestCard(cards)
    highestCard should be (cards.maxBy(c => deck.cards.indexOf(c)))

  test("getHighestCard throws an IllegalArgumentException if the given cards are not in the deck"):
    deck.addCard(cards(1))
    a [IllegalArgumentException] should be thrownBy deck.getHighestCard(List(cards(2)))
