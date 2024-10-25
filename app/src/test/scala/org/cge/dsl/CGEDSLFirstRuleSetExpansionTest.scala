package org.cge.dsl

import org.scalatest.matchers.should.Matchers._
import org.cge.dsl.CardGameEngineDSL.game
import org.cge.dsl.CardGameEngineDSL.suitsAre
import org.cge.dsl.CardGameEngineDSL.ranksAre
import org.cge.engine.model._
import org.cge.dsl.CardGameEngineDSL.trumpIs
import org.cge.engine.model.GameModel.WinCondition
import org.cge.dsl.CardGameEngineDSL.win
import org.cge.dsl.SyntacticSugar.conditions
import org.cge.dsl.CardGameEngineDSL.gives
import org.cge.dsl.SyntacticSugar.to

class CGEDSLFirstRuleSetExpansionTest extends CardGameEngineDSLTest:

  test("suitsAre should let you choose cards suits"):
    val g = game suitsAre ("Clubs", "Diamonds", "Hearts", "Spades")
    g match
      case g: PuppetBuilder =>
        g.cardSuits shouldBe Set[Suit]("Clubs", "Diamonds", "Hearts", "Spades")
      case _ => fail(wrongClassText)

  // test("suitsAre cannot receive an empty set"):
  //   intercept[IllegalArgumentException] {
  //     game suitsAre ()
  //   }

  test("ranksAre should let you choose cards ranks"):
    val g =
      game ranksAre ("Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King")
    g match
      case g: PuppetBuilder =>
        g.cardRanks shouldBe List(
          "Ace",
          "Two",
          "Three",
          "Four",
          "Five",
          "Six",
          "Seven",
          "Eight",
          "Nine",
          "Ten",
          "Jack",
          "Queen",
          "King"
        )
      case _ => fail(wrongClassText)

  test("trumpIs should set the trump suit"):
    val g = game trumpIs "Hearts"
    g match
      case g: PuppetBuilder =>
        g.trump shouldBe Some("Hearts")
      case _ => fail(wrongClassText)

  test("gives <number: Int> cards to player <playerName: String> should forward to cardsInHandPerPlayer"):
    val g = game gives 5 cards to player "Test"
    val builder = new PuppetBuilder()
    builder.cardsInHandPerPlayer(() => 5, "Test")
    g match 
      case g: PuppetBuilder => g.cardsInHandPerPlayer("Test")() shouldBe builder.cardsInHandPerPlayer("Test")()
      case _ => fail(wrongClassText)

  test("win conditions are should set win conditions"):
    val wc1: WinCondition = (game, player) => true || game == player
    val wc2: WinCondition = (game, player) => true || game == player
    val g = game win conditions are (wc1, wc2)
    g match
      case g: PuppetBuilder => g.winConditions should be (List(wc1, wc2))
      case _ => fail("game is not a PuppetBuilder")
