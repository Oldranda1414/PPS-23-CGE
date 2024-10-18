package org.cge.engine

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach
import org.cge.engine.model.Clubs
import org.cge.engine.model.Spades
import org.cge.engine.data.StandardDeck
import org.cge.engine.model.Two
import org.cge.engine.model.Three
import org.cge.engine.model.Jack
import org.cge.engine.model.Queen

class GameBuilderTest extends AnyTest with BeforeAndAfterEach:

  var _gameBuilder: GameBuilder = GameBuilder()

  override def beforeEach() =
    _gameBuilder = GameBuilder()

  test("build should throw an exception if the game name is not set"):
    intercept[IllegalStateException] {
      _gameBuilder.build
    }
  
  test("build should throw an exception if the number of cards in hand is not set"):
    _gameBuilder.setName("Game name")
    intercept[IllegalStateException] {
      _gameBuilder.build
    }

  test("build should throw an exception if the number of players is not set"):
    _gameBuilder.setName("Game name")
    _gameBuilder.cardsInHand(() => 5)
    intercept[IllegalStateException] {
      _gameBuilder.build
    }

  test("build should return a game with the specified name, number of players, and number of cards in hand"):
    _gameBuilder.setName("Game name")
    _gameBuilder.addPlayer("Player 1")
    _gameBuilder.addPlayer("Player 2")
    _gameBuilder.cardsInHand(() => 5)
    val game = _gameBuilder.build
    game.name should be ("Game name")
    game.players.size should be (2)
    game.players.foreach(player => player.hand.cards.size should be (5))

  test("cannot set the name with empty or blank strings"):
    intercept[IllegalArgumentException] {
      _gameBuilder.setName("")
    }
    intercept[IllegalArgumentException] {
      _gameBuilder.setName(" ")
    }

  test("cannot set the name twice"):
    _gameBuilder.setName("Game name")
    intercept[IllegalArgumentException] {
      _gameBuilder.setName("Game name")
    }

  test("cannot add a player with an empty or blank string"):
    intercept[IllegalArgumentException] {
      _gameBuilder.addPlayer("")
    }
    intercept[IllegalArgumentException] {
      _gameBuilder.addPlayer(" ")
    }

  test("cannot add the same player twice"):
    val playerName = "Player 1"
    _gameBuilder.addPlayer(playerName)
    intercept[IllegalArgumentException] {
      _gameBuilder.addPlayer(playerName)
    }

  test("cannot set the number of cards in hand twice"):
    _gameBuilder.cardsInHand(() => 5)
    intercept[IllegalArgumentException] {
      _gameBuilder.cardsInHand(() => 5)
    }

  test("cannot set the number of cards in hand under 1"):
    intercept[IllegalArgumentException] {
      _gameBuilder.cardsInHand(() => 0)
    }

  test("cannot add a suit twice"):
    val suit = Clubs
    _gameBuilder.addSuit(suit)
    intercept[IllegalArgumentException] {
      _gameBuilder.addSuit(suit)
    }

  test("add suits creates a deck with the specified suits"):
    _gameBuilder.addSuit(Clubs)
    _gameBuilder.addSuit(Spades)
    val numberOfSuits = 2
    val numOfCards = 13 * numberOfSuits
    _gameBuilder.setName("Game name")
    _gameBuilder.addPlayer("Player 1")
    _gameBuilder.cardsInHand(() => 5)
    _gameBuilder.currentGameCards.size should be (numOfCards)

  test("not adding a suit will use the standard deck"):
    _gameBuilder.currentGameCards should be (StandardDeck.cards)

  test("cannot add ranks twice"):
    val ranks = List(Two, Three, Jack, Queen)
    _gameBuilder.addOrderedRanks(ranks)
    intercept[IllegalArgumentException] {
      _gameBuilder.addOrderedRanks(ranks)
    }
  
  test("add ranks creates a deck with the specified ranks"):
    val ranks = List(Two, Three, Jack, Queen)
    _gameBuilder.addOrderedRanks(ranks)
    _gameBuilder.setName("Game name")
    _gameBuilder.addPlayer("Player 1")
    _gameBuilder.cardsInHand(() => 5)
    val numOfSuits = 4
    val numOfRanks = _gameBuilder.currentGameCards.size / numOfSuits
    numOfRanks should be (ranks.size)

  

