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
import org.cge.dsl.SyntacticSugar.from
import org.cge.dsl.CardGameEngineDSL.starts
import org.cge.dsl.CardGameEngineDSL.playing
import org.cge.dsl.SyntacticSugar.player
import org.cge.dsl.CardGameEngineDSL.has
import org.cge.engine.model.TableModel.HandRule
import org.cge.dsl.CardGameEngineDSL.hand
import org.cge.dsl.SyntacticSugar.rules
import org.cge.dsl.exception.CGESyntaxError

class CGEDSLFirstRuleSetExpansionTest extends CardGameEngineDSLTest:

  test("suitsAre should let you choose cards suits"):
    val g = game suitsAre ("Clubs", "Diamonds", "Hearts", "Spades")
    g match
      case g: PuppetBuilder =>
        g.cardSuits shouldBe Set[Suit]("Clubs", "Diamonds", "Hearts", "Spades")
      case _ => fail(wrongClassText)

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

  test(
    "gives <number: Int> cards to player <playerName: String> should forward to cardsInHandPerPlayer"
  ):
    val g = game gives 5 cards to player "Test"
    val builder = new PuppetBuilder()
    builder.cardsInHandPerPlayer(() => 5, "Test")
    g match
      case g: PuppetBuilder =>
        g.cardsInHandPerPlayer("Test")() shouldBe builder.cardsInHandPerPlayer(
          "Test"
        )()
      case _ => fail(wrongClassText)

  test("starts from player <playername: String> should forward to starterPlayer"):
    val g = game starts from player "Test"
    val builder = new PuppetBuilder()
    builder.starterPlayer("Test")
    g match
      case g: PuppetBuilder =>
        g.starter shouldBe builder.starter
      case _ => fail(wrongClassText)

  test("starts from random player should forward to starterPlayer with a random player"):
    isRandom (() =>
        CardGameEngineDSL(new PuppetBuilder())
        game has player called "P1"
        game has player called "P2"
        val g = game starts from random player
        g match
          case g: PuppetBuilder =>
            g.players.indexOf(g.starter)
          case _ => fail(wrongClassText)
    )() should be(true)

  test("starts from random player should thorw an exception if there are no players"):
    game match
      case g: PuppetBuilder =>
        a [CGESyntaxError] should be thrownBy (g starts from random player)
      case _ => fail(wrongClassText)

  test("game playing rules are should add a playing rule"):
    val rule = (table: TableModel, card: CardModel) =>
      table.deck.getHighestCard(table.cardsOnTable :+ card) == card
    val g = game playing rules are (
      rule
    )
    val builder = new PuppetBuilder()
    builder.addPlayingRule(rule)
    g match
      case g: PuppetBuilder =>
        g.rules should contain theSameElementsAs builder.rules
      case _ => fail(wrongClassText)

  test("win conditions are should set win conditions"):
    val wc1: WinCondition = (game, player) => true || game == player
    val wc2: WinCondition = (game, player) => true || game == player
    val g = game win conditions are (wc1, wc2)
    g match
      case g: PuppetBuilder => g.winConditions should be (List(wc1, wc2))
      case _ => fail("game is not a PuppetBuilder")

  test("hand rules are should set hand rules"):
    val hr1: HandRule = (cardsOnTable, card, trump) =>
      true || cardsOnTable == card
    val hr2: HandRule = (cardsOnTable, card, trump) =>
      true || cardsOnTable == card
    val g = game hand rules are (hr1, hr2)
    g match
      case g: PuppetBuilder => g.table.handRules should be (List(hr1, hr2))
      case _ => fail("game is not a PuppetBuilder")
