package org.cge.dsl

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.cge.dsl.CardGameEngineDSL._
import org.cge.engine.GameBuilder
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.model.GameModel

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
    
