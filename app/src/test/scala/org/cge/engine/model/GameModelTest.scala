package org.cge.engine.model

import org.cge.AnyTest
import org.cge.engine.model.GameModel._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.data.StandardDeck

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
  private var card: CardModel = CardModel(Two, Clubs)

  override def beforeEach(): Unit =
    card = CardModel(Two, Clubs)

  test("SimpleCard rank should be Two"):
    card.rank should be (Two)

  test("SimpleCard suit should be Clubs"):
    card.suit should be (Clubs)

class PlayerModelTest extends AnyTest with BeforeAndAfterEach:
  private var player: PlayerModel = PlayerModel("name")
  private var card: CardModel = CardModel(Ace, Spades)

  override def beforeEach(): Unit =
    player = PlayerModel("name")
    card = CardModel(Ace, Spades)

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

class DeckModelTest extends AnyTest with BeforeAndAfterEach:
  private var deck: DeckModel = DeckModel()
  private val cards: List[CardModel] = StandardDeck.ranks.map(r => CardModel(r, Spades))
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
    val drawnCards = deck.removeCards(numberOfDrawnCards)
    drawnCards should be (cards.take(numberOfDrawnCards))

  test("Drawing from a SimpleDeck removes the first N cards of the deck"):
    cards.foreach(deck.addCard(_))
    val drawnCards = deck.removeCards(numberOfDrawnCards)
    (drawnCards ++ deck.cards) should be (cards)

class TableModelTest extends AnyTest with BeforeAndAfterEach:
  private val card: CardModel = CardModel("1", "Spades")
  
  private class PuppetDeck() extends DeckModel:
    private var testValue: Int = 0
    def cards: List[CardModel] = testValue match
      case 0 => List(card)
      case 1 => List(card, card)
    def addCard(card: CardModel): Unit = testValue = 1
    def removeCards(numberOfCards: Int): List[CardModel] = List(card)
  
  val table: TableModel = TableModel(PuppetDeck())

  test("Testing TableModel.cardsOnTable.cards"):
    table.cardsOnTable.cards should be (List(card))
  
  test("Testing TableModel.cardsOnTable.addCard"):
    table.cardsOnTable.addCard(card)
    table.cardsOnTable.cards should be (List(card, card))
  
  test("Testing TableModel.cardsOnTable.removeCards"):
    table.cardsOnTable.removeCards(1) should be (List(card))
