package org.cge.dsl

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.cge.dsl.CardGameEngineDSL._
import org.cge.engine.GameBuilder
import org.scalatest.BeforeAndAfterEach
import org.cge.dsl.SyntacticSugar._
import org.cge.engine.model._
import org.cge.dsl.SyntacticBuilder.PlayerBuilder
import org.cge.dsl.SyntacticBuilder.CountCardBuilder
import org.cge.dsl.SyntacticBuilder.CardSyntSugarBuilder

class CardGameEngineDSLTest extends AnyTest with BeforeAndAfterEach:  

  protected class PuppetBuilder extends GameBuilder:
    var name = ""
    var players = List.empty[String]
    var numberOfCards = () => 0
    var cardSuits = Set.empty[Suit]
    var cardRanks = List.empty[Rank]
    var trump: Option[Suit] = None
    var cardsInHandPerPlayer: Map[String, () => Int] = Map.empty

    override def setName(name: String): GameBuilder = 
      this.name = name
      this

    override def addPlayer(name: String): GameBuilder = 
      this.players = players
      this

    override def cardsInHand(numberOfCards: () => Int): GameBuilder = 
      this.numberOfCards = numberOfCards
      this


    override def addSuit(suit: Suit): GameBuilder = 
      cardSuits = cardSuits + suit
      this

    override def addSortedRanks(ranks: List[Rank]): GameBuilder = 
      cardRanks = ranks
      this

    override def setTrump(suit: Suit): GameBuilder =
      trump = Some(suit)
      this

    override def starterPlayer(player: String): GameBuilder = ???

    override def cardsInHandPerPlayer(numberOfCards: () => Int, player: String): GameBuilder =
      cardsInHandPerPlayer = cardsInHandPerPlayer + (player -> numberOfCards)
      this

    override def currentGameCards: List[CardModel] = List.empty


    override def build: GameModel = GameModel("Puppet")

  override def beforeEach(): Unit = 
    CardGameEngineDSL(new PuppetBuilder())

  test("game word retreives GameBuilder"):
    game shouldBe a [GameBuilder]

  test("is word should forward to setName"):
    val g = game is "Test"
    val builder = new PuppetBuilder()
    builder.setName("Test")
    println(g.getClass())
    g match 
      case g: PuppetBuilder => g.name shouldBe builder.name
      case _ => fail("game is not a PuppetBuilder")

  test("has player word should return a PlayerBuilder"):
    val g = game has player
    g shouldBe a [PlayerBuilder]

  test("has player called word should forward to addPlayer"):
    val g = game has player called "Test"
    val builder = new PuppetBuilder()
    builder.addPlayer("Test")
    g match 
      case g: PuppetBuilder => g.players shouldBe builder.players
      case _ => fail("game is not a PuppetBuilder")

  test("gives <number: Int> should return a CountCardBuilder"):
    val g = game gives 5 
    g shouldBe a [CountCardBuilder]

  test("gives <number: Int> cards to should return a EachSyntSugarBuilder"):
    val g = game gives 5 cards to
    g shouldBe a [CardSyntSugarBuilder]

  test("gives <number: Int> cards to each player should forward to cardsInHand"):
    val g = game gives 5 cards to each player
    val builder = new PuppetBuilder()
    builder.cardsInHand(() => 5)
    g match 
      case g: PuppetBuilder => g.numberOfCards() shouldBe builder.numberOfCards()
      case _ => fail("game is not a PuppetBuilder")

  test("gives random cards to each player should use random values"):
    val g = game gives random cards to each player
    g match 
      case g: PuppetBuilder => isRandom(g.numberOfCards, 10) shouldBe true
      case _ => fail("game is not a PuppetBuilder")
    
  /** Check if a function returns random values based on heuristic */
  private def isRandom(f: () => Int, trials: Int = 5): Boolean =
    val results = (1 to trials).map(_ => f())
    results.distinct.size > 1