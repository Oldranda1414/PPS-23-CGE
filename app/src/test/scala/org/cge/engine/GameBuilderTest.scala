package org.cge.engine

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.model.Suit
import org.cge.engine.model.Rank
import org.cge.engine.model.GameModel.TableGameWithWinConditions
import org.cge.engine.model.CardModel
import org.cge.engine.model.TableModel
import org.cge.engine.model.GameModel.WinCondition
import org.cge.engine.model.TableModel.HandRule
import org.cge.engine.model.PlayerModel

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
    a [IllegalStateException] should be thrownBy gameBuilder.build
  
  test("build should throw an exception if the number of cards in hand is not set"):
    gameBuilder.setName(gameName)
    a [IllegalStateException] should be thrownBy gameBuilder.build

  test("build should throw an exception if the number of players is not set"):
    gameBuilder.setName(gameName)
    gameBuilder.cardsInHand(() => 5)
    a [IllegalStateException] should be thrownBy gameBuilder.build

  test("build should throw an exception if suits are not set"):
    gameBuilder.setName(gameName)
    gameBuilder.addPlayer(playerName)
    gameBuilder.cardsInHand(() => 5)
    a [IllegalStateException] should be thrownBy gameBuilder.build

  test("build should throw an exception if ranks are not set"):
    gameBuilder.setName(gameName)
    gameBuilder.addPlayer(playerName)
    gameBuilder.cardsInHand(() => 5)
    gameBuilder.addSuit("Clubs")
    a [IllegalStateException] should be thrownBy gameBuilder.build

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
    a [IllegalArgumentException] should be thrownBy gameBuilder.setName("")
    a [IllegalArgumentException] should be thrownBy gameBuilder.setName(" ")

  test("at start current game cards is an empty list"):
    gameBuilder.currentGameCards.size should be (0)

  test("cannot set the name twice"):
    gameBuilder.setName(gameName)
    a [IllegalArgumentException] should be thrownBy gameBuilder.setName(gameName)

  test("cannot add a player with an empty or blank string"):
    a [IllegalArgumentException] should be thrownBy gameBuilder.addPlayer("")
    a [IllegalArgumentException] should be thrownBy gameBuilder.addPlayer(" ")

  test("cannot add the same player twice"):
    gameBuilder.addPlayer(playerName)
    a [IllegalArgumentException] should be thrownBy gameBuilder.addPlayer(playerName)

  test("cannot set the number of cards in hand twice"):
    gameBuilder.cardsInHand(() => 5)
    a [IllegalArgumentException] should be thrownBy gameBuilder.cardsInHand(() => 5)

  test("cannot set the number of cards in hand under 1"):
    a [IllegalArgumentException] should be thrownBy gameBuilder.cardsInHand(() => 0)

  test("cannot add a suit twice"):
    gameBuilder.addSuit(suits(0))
    a [IllegalArgumentException] should be thrownBy gameBuilder.addSuit(suits(0))

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
    a [IllegalArgumentException] should be thrownBy gameBuilder.addSortedRanks(ranks)
  
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
    a [IllegalArgumentException] should be thrownBy gameBuilder.addSuit(suits(0))
        .setName(gameName)
        .addPlayer(playerName)
        .cardsInHand(() => 5)
        .addSortedRanks(ranks)
        .setTrump("InvalidTrump")
        .build

  test("trump cannot be set twice"):
    gameBuilder.addSuit(trump)
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHand(() => 5)
      .addSortedRanks(ranks)
      .setTrump(trump)
    a [IllegalArgumentException] should be thrownBy gameBuilder.setTrump(trump)

  test("exception will throw if sum of cards in hand is greater than the number of cards in the deck"):
    gameBuilder.addSortedRanks(ranks.filter(ranks.indexOf(_) < 4))
      .addSuit(suits(0))
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHand(() => 5)
    a [IllegalArgumentException] should be thrownBy gameBuilder.build

  test("a win condition can be added"):
    val wc1: WinCondition = (game, player) => true || game == player
    gameBuilder.addSuit("Clubs")
      .setName("Game name")
      .addPlayer("Player 1")
      .cardsInHand(() => 5)
      .addSortedRanks(List("Two", "Three", "Jack", "Queen", "King"))
      .addWinCondition(wc1)
    val game = gameBuilder.build
    game.winConditions should be (List(wc1))

  test("a hand rule can be added"):
    val hr1: HandRule = (cardsOnTable, card, trump) => true || cardsOnTable == card
    gameBuilder.addSuit(suits(1))
      .setName("Game name")
      .addPlayer("Player 1")
      .cardsInHand(() => 5)
      .addSortedRanks(List("Two", "Three", "Jack", "Queen", "King"))
      .addHandRule(hr1)
    val game = gameBuilder.build
    game.table.handRules should be (List(hr1))

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
    a [IllegalArgumentException] should be thrownBy gameBuilder.cardsInHandPerPlayer(() => 5, playerName)

  test("cards in hand per player cannot be called if cards in hand is already set"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHand(() => 5)
    a [IllegalArgumentException] should be thrownBy gameBuilder.cardsInHandPerPlayer(() => 5, playerName)

  test("cards in hand cannot be called if cards in hand per palyer is already set"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName(gameName)
      .addPlayer(playerName)
      .cardsInHandPerPlayer(() => 5, playerName)
    a [IllegalArgumentException] should be thrownBy gameBuilder.cardsInHand(() => 5)

  test("cards in hand cannot set the number of cards in hand for not existing player"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName(gameName)
      .addPlayer(playerName)
    a [IllegalArgumentException] should be thrownBy gameBuilder.cardsInHandPerPlayer(() => 5, player2Name)

  test("all players must have a number of cards set"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName(gameName)
      .addPlayer(playerName)
      .addPlayer(player2Name)
      .cardsInHandPerPlayer(() => 5, playerName)
    a [IllegalStateException] should be thrownBy gameBuilder.build

  test("starter player sets the player that starts the game"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName("Game name")
      .addPlayer(playerName)
      .cardsInHandPerPlayer(() => 5, playerName)
      .addPlayer(player2Name)
      .cardsInHandPerPlayer(() => 5, player2Name)
      .starterPlayer(player2Name)
    val game = gameBuilder.build
    game match
      case game: TableGameWithWinConditions => 
        game.turn should be (game.players(1))
      case _ => fail("Game is not a TableGame")

  test("starter player cannot be set twice"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName("Game name")
      .addPlayer(playerName)
      .cardsInHandPerPlayer(() => 5, playerName)
      .starterPlayer(playerName)
    a [IllegalArgumentException] should be thrownBy gameBuilder.starterPlayer(playerName)

  test("starter player cannot be someone that is not in the game"):
    gameBuilder.addSortedRanks(ranks)
      .addSuit(suits(0)).addSuit(suits(1))
      .setName("Game name")
      .addPlayer(playerName)
      .cardsInHandPerPlayer(() => 5, playerName)
    a [IllegalArgumentException] should be thrownBy gameBuilder.starterPlayer(player2Name)

  test("can build playing rules for the game"):
    val rule = (_: TableModel, _: CardModel) => true
    gameBuilder.addPlayingRule(rule)
      .addPlayer(playerName)
      .cardsInHand(() => 5)
      .setName(gameName)
      .addSortedRanks(ranks)
      .addSuit(suits(0))
    val game = gameBuilder.build
    game match
      case game: TableGameWithWinConditions => 
        game.table.playingRules.size should be (1)
      case _ => fail("Game is not a TableGame")

  test("currentPlayers returns the current players that are in the game"):
    gameBuilder.addPlayer(playerName)
      .addPlayer(player2Name)
    gameBuilder.currentPlayers.size should be (2)
    gameBuilder.currentPlayers should contain allOf (PlayerModel(playerName), PlayerModel(player2Name))
