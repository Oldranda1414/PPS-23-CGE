package org.cge.dsl

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.cge.dsl.CardGameEngineDSL._
import org.cge.engine.GameBuilder
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.model.GameModel
import org.cge.dsl.SyntacticSugar._

class CardGameEngineDSLTest extends AnyTest with BeforeAndAfterEach:  

  class PuppetBuilder extends GameBuilder:

    var name = ""
    var players = List.empty[String]
    var numberOfCards = () => 0

    override def setName(name: String): GameBuilder = 
      this.name = name
      this

    override def addPlayer(name: String): GameBuilder = 
      players = players
      this

    override def cardsInHand(numberOfCards: () => Int): GameBuilder = 
      this.numberOfCards = numberOfCards
      this

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
    g shouldBe a [EachSyntSugarBuilder]

  test("gives <number: Int> cards to each player should forward to cardsInHand"):
    val g = game gives 5 cards to each player
    val builder = new PuppetBuilder()
    builder.cardsInHand(() => 5)
    g match 
      case g: PuppetBuilder => g.numberOfCards() shouldBe builder.numberOfCards()
      case _ => fail("game is not a PuppetBuilder")

  test("gives random cards to each player should use random values"):
    val g = game gives random to each player
    g match 
      case g: PuppetBuilder => isRandom(g.numberOfCards, 10) shouldBe true
      case _ => fail("game is not a PuppetBuilder")

  private def isRandom(f: () => Int, trials: Int = 5): Boolean =
    val results = (1 to trials).map(_ => f())
    results.distinct.size > 1