package org.cge.engine

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.model.Suit
import org.cge.engine.model.Rank

class GameBuilderTest extends AnyTest with BeforeAndAfterEach:

  var gameBuilder: GameBuilder = GameBuilder()
  val ranks = List[Rank]("Two", "Three", "Four", "Five", "Jack", "Queen", "King", "Ace")
  val suits = List[Suit]("Clubs", "Spades", "Hearts", "Diamonds")
  val trump: Suit = "Clubs"
  val gameName = "Test"
  val playerName = "Player 1"
  val player2Name = "Player 2"

  override def beforeEach() =
    gameBuilder = GameBuilder()

  test("build should throw an exception if the game name is not set"):
    intercept[IllegalStateException] {
      gameBuilder.build
    }
  
  test("build should throw an exception if the number of cards in hand is not set"):
    gameBuilder.setName(gameName)
    intercept[IllegalStateException] {
      gameBuilder.build
    }

  test("build should throw an exception if the number of players is not set"):
    gameBuilder.setName(gameName)
    gameBuilder.cardsInHand(() => 5)
    intercept[IllegalStateException] {
      gameBuilder.build
    }

  test("build should throw an exception if suits are not set"):
    gameBuilder.setName(gameName)
    gameBuilder.addPlayer(playerName)
    gameBuilder.cardsInHand(() => 5)
    intercept[IllegalStateException] {
      gameBuilder.build
    }

  test("build should throw an exception if ranks are not set"):
    gameBuilder.setName(gameName)
    gameBuilder.addPlayer(playerName)
    gameBuilder.cardsInHand(() => 5)
    gameBuilder.addSuit("Clubs")
    intercept[IllegalStateException] {
      gameBuilder.build
    }

  test("build should return a valid game"):
    val game = gameBuilder.setName(gameName)
      .addPlayer(playerName)
      .addPlayer(player2Name)
      .cardsInHand(() => 5)
      .addSuit("Clubs").addSuit("Spades").addSuit("Hearts").addSuit("Diamonds")
      .addSortedRanks(List("Two", "Three", "Jack", "Queen"))
      .build
    game.name should be (gameName)
    game.players.size should be (2)
    game.players.foreach(player => player.hand.cards.size should be (5))

  test("cannot set the name with empty or blank strings"):
    intercept[IllegalArgumentException] {
      gameBuilder.setName("")
    }
    intercept[IllegalArgumentException] {
      gameBuilder.setName(" ")
    }

  test("at start current game cards is an empty list"):
    gameBuilder.currentGameCards.size should be (0)

  test("cannot set the name twice"):
    gameBuilder.setName(gameName)
    intercept[IllegalArgumentException] {
      gameBuilder.setName(gameName)
    }

  test("cannot add a player with an empty or blank string"):
    intercept[IllegalArgumentException] {
      gameBuilder.addPlayer("")
    }
    intercept[IllegalArgumentException] {
      gameBuilder.addPlayer(" ")
    }

  test("cannot add the same player twice"):
    gameBuilder.addPlayer(playerName)
    intercept[IllegalArgumentException] {
      gameBuilder.addPlayer(playerName)
    }

  test("cannot set the number of cards in hand twice"):
    gameBuilder.cardsInHand(() => 5)
    intercept[IllegalArgumentException] {
      gameBuilder.cardsInHand(() => 5)
    }

  test("cannot set the number of cards in hand under 1"):
    intercept[IllegalArgumentException] {
      gameBuilder.cardsInHand(() => 0)
    }

  test("cannot add a suit twice"):
    gameBuilder.addSuit(suits(0))
    intercept[IllegalArgumentException] {
      gameBuilder.addSuit(suits(0))
    }

  test("add suits creates a deck with the specified suits"):
    gameBuilder.addSuit(suits(0)).addSuit(suits(1))
      .addSortedRanks(ranks)
    val numberOfSuits = 2
    val numOfCards = ranks.size * numberOfSuits
    gameBuilder.setName(gameName)
    gameBuilder.addPlayer(playerName)
    gameBuilder.cardsInHand(() => 5)
    gameBuilder.currentGameCards.size should be (numOfCards)

  test("cannot add ranks twice"):
    gameBuilder.addSortedRanks(ranks)
    intercept[IllegalArgumentException] {
      gameBuilder.addSortedRanks(ranks)
    }
  
  test("add ranks creates a deck with the specified ranks"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0))
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHand(() => 5)
    val numOfSuits = 1
    val numOfRanks = gameBuilder.currentGameCards.size / numOfSuits
    numOfRanks should be (ranks.size)

  test("trump can be set"):
    gameBuilder.addSuit(trump)
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHand(() => 5)
      .addSortedRanks(ranks)
      .setTrump(trump)
    val game = gameBuilder.build
    game.trump should be (Option(trump))

  test("trump cannot be set to a suit that is not in the game"):
    intercept[IllegalArgumentException] {
      gameBuilder.addSuit(suits(0))
        .setName(gameName)
        .addPlayer(playerName)
        .cardsInHand(() => 5)
        .addSortedRanks(ranks)
        .setTrump("InvalidTrump")
        .build
    }

  test("exception will throw if sum of cards in hand is greater than the number of cards in the deck"):
    gameBuilder.addSortedRanks(ranks.filter(ranks.indexOf(_) < 4))
      .addSuit(suits(0))
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHand(() => 5)
    intercept[IllegalArgumentException] {
      gameBuilder.build
    }

  test("cards in hand per player can set each player number of cards"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHandPerPlayer(() => 5, playerName)
      .addPlayer(player2Name)
      .cardsInHandPerPlayer(() => 3, player2Name)
    val game = gameBuilder.build
    game.players.head.hand.cards.size should be (5)
    game.players(1).hand.cards.size should be (3)

  test("cards in hand per player cannot be set twice for the same player"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHandPerPlayer(() => 5, playerName)
    intercept[IllegalArgumentException] {
      gameBuilder.cardsInHandPerPlayer(() => 5, playerName)
    }

  test("cards in hand per player cannot be called if cards in hand is already set"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHand(() => 5)
    intercept[IllegalArgumentException] {
      gameBuilder.cardsInHandPerPlayer(() => 5, playerName)
    }

  test("cards in hand cannot be called if cards in hand per palyer is already set"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHandPerPlayer(() => 5, playerName)
    intercept[IllegalArgumentException] {
      gameBuilder.cardsInHand(() => 5)
    }

  test("cards in hand cannot set the number of cards in hand for not existing player"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName(gameName)
      .addPlayer(playerName)
    intercept[IllegalArgumentException] {
      gameBuilder.cardsInHandPerPlayer(() => 5, player2Name)
    }

  test("all players must have a number of cards set"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName(gameName)
      .addPlayer(playerName)
      .addPlayer(player2Name)
      .cardsInHandPerPlayer(() => 5, playerName)
    intercept[IllegalStateException] {
      gameBuilder.build
    }