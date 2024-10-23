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

