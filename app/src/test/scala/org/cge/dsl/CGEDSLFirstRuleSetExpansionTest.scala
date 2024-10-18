package org.cge.dsl

import org.scalatest.matchers.should.Matchers._
import org.cge.dsl.CardGameEngineDSL.game
import org.cge.dsl.CardGameEngineDSL.suitsAre
import org.cge.dsl.CardGameEngineDSL.ranksAre
import org.cge.engine.model._
import org.cge.dsl.SyntacticSugar.StandardSuits
import org.cge.dsl.SyntacticSugar.StandardRanks

class CGEDSLFirstRuleSetExpansionTest extends CardGameEngineDSLTest:

  test("suitsAre should let you choose cards suits"):
    val g = game suitsAre (Clubs, Diamonds, Hearts, Spades)
    g match
      case g: PuppetBuilder =>
        g.cardSuits shouldBe Set(Clubs, Diamonds, Hearts, Spades)
      case _ => fail("game is not a PuppetBuilder")

  test("ranksAre should let you choose cards ranks"):
    val g =
      game ranksAre (Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King)
    g match
      case g: PuppetBuilder =>
        g.cardRanks shouldBe List(
          Ace,
          Two,
          Three,
          Four,
          Five,
          Six,
          Seven,
          Eight,
          Nine,
          Ten,
          Jack,
          Queen,
          King
        )
      case _ => fail("game is not a PuppetBuilder")

  test("suitsAre StandardSuits should set the standard suits"):
    val g = game suitsAre StandardSuits
    g match
      case g: PuppetBuilder =>
        g.cardSuits shouldBe Set(Clubs, Diamonds, Hearts, Spades)
      case _ => fail("game is not a PuppetBuilder")

  test("ranksAre StandardRanks should set the standard ranks"):
    val g = game ranksAre StandardRanks
    g match
      case g: PuppetBuilder =>
        g.cardRanks shouldBe List(
          Two,
          Three,
          Four,
          Five,
          Six,
          Seven,
          Eight,
          Nine,
          Ten,
          Jack,
          Queen,
          King,
          Ace
        )
      case _ => fail("game is not a PuppetBuilder")
