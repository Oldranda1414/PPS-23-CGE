package org.cge.engine.model

import org.cge.AnyTest
import org.cge.engine.model.GameModel._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class GameModelTest extends AnyTest with BeforeAndAfterEach:
  private var gameModel: GameModel = SimpleGame("simple game")
  private val testPlayer = PlayerModel("Test")

  override def beforeEach(): Unit =
    gameModel = SimpleGame("simple game")

  test("GameModel should be able to add a named player"):
    gameModel.addPlayer(testPlayer)
    gameModel.players should be (List(testPlayer))

  test("GameModel should be able to remove a named player"):
    gameModel.addPlayer(testPlayer)
    gameModel.removePlayer(testPlayer)
    gameModel.players should be (List.empty[PlayerModel])

  test("GameModel should be able to add multiple named players"):
    val testPlayer2: PlayerModel = PlayerModel("Test2")

    gameModel.addPlayer(testPlayer)
    gameModel.addPlayer(testPlayer2)
    gameModel.players should be (List(testPlayer, testPlayer2))

  test("GameModel should have initialized name"):
    gameModel.name should be ("simple game")

class CardModelTest extends AnyTest with BeforeAndAfterEach:
  private var card: CardModel = CardModel("value", Clubs)

  override def beforeEach(): Unit =
    card = CardModel("value", Clubs)

  test("SimpleCard value should be \"value\""):
    card.value should be ("value")

  test("SimpleCard suit should be Clubs"):
    card.suit should be (Clubs)

class PlayerModelTest extends AnyTest with BeforeAndAfterEach:
  private var player: PlayerModel = PlayerModel("name")
  private var card: CardModel = CardModel("1", Spades)

  override def beforeEach(): Unit =
    player = PlayerModel("name")
    card = CardModel("1", Spades)

  test("SimplePlayer name should be \"name\""):
    player.name should be ("name")

  test("SimplePlayer cards set should be empty after initialization"):
    player.deck.cards should be (List[CardModel]())
  
  test("SimplePlayer cards set should be non-empty after addCard"):
    player.deck.addCard(card)
    player.deck.cards should not be (List[CardModel]())

  test("SimplePlayer cards set should contain the added card after addCard"):
    player.deck.addCard(card)
    player.deck.cards should contain (card)

class DeckModelTest extends AnyTest with BeforeAndAfterEach:
  private var deck: DeckModel = DeckModel()
  private val cards: List[CardModel] =
      Range(1, 10)
      .toList
      .map(e => CardModel(e.toString, Spades))
  private val card: CardModel = cards.head
  private val numberOfDrawnCards: Int = 3

  override def beforeEach(): Unit =
    deck = DeckModel()

  test("SimpleDeck should be non-empty after addCard"):
    deck.addCard(card)
    deck.cards should not be (List[CardModel]())

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
