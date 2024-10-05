package org.cge.engine

import org.cge.AnyTest
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

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
    _gameBuilder.cardsInHand(5)
    intercept[IllegalStateException] {
      _gameBuilder.build
    }

  test("build should return a game with the specified name, number of players, and number of cards in hand"):
    _gameBuilder.setName("Game name")
    _gameBuilder.addPlayer("Player 1")
    _gameBuilder.addPlayer("Player 2")
    _gameBuilder.cardsInHand(5)
    val game = _gameBuilder.build
    game.name should be ("Game name")
    game.players.size should be (2)
    game.players.foreach(player => player.deck.cards.size should be (5))

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
    _gameBuilder.cardsInHand(5)
    intercept[IllegalArgumentException] {
      _gameBuilder.cardsInHand(5)
    }

  test("cannot set the number of cards in hand under 1"):
    intercept[IllegalArgumentException] {
      _gameBuilder.cardsInHand(0)
    }
