package org.cge.engine.model

import org.cge.AnyTest
import org.cge.engine.model.GameModel._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class GameModelTest extends AnyTest with BeforeAndAfterEach:
  private var game: GameModel = GameModel("simple game")
  private val testPlayer = PlayerModel("Test")
  private val winner1 = PlayerModel("winner1")
  private val winner2 = PlayerModel("winner2")
  private val looser1 = PlayerModel("looser1")
  private val looser2 = PlayerModel("looser2")
  private val winCondition: WinCondition =
    (game, player) => player.name.contains("winner")

  private def _addPlayersToGame(players: List[PlayerModel]) =
    players.foreach(game.addPlayer(_))

  override def beforeEach(): Unit =
    game = GameModel("simple game")

  test("GameModel should be able to add a named player"):
    game.addPlayer(testPlayer)
    game.players should be (List(testPlayer))

  test("GameModel should be able to remove a named player"):
    game.addPlayer(testPlayer)
    game.removePlayer(testPlayer)
    game.players should be (List[PlayerModel]())

  test("GameModel should be able to add multiple named players"):
    val testPlayer2: PlayerModel = PlayerModel("Test2")

    game.addPlayer(testPlayer)
    game.addPlayer(testPlayer2)
    game.players should be (List(testPlayer, testPlayer2))

  test("GameModel should have initialized name"):
    game.name should be ("simple game")

  test("GameModel trump should be empty after initializazion"):
    game.trump should be (None)

  test("GameModel trump should change after setting it"):
    game.trump = Spades
    game.trump should be (Some(Spades))

  test("GameModel should have a table"):
      game.table mustBe a[TableModel]

  test("TableGameWithWinConditions should start with an empty list of win conditions"):
    game.winConditions should be (List[WinCondition]())

  test("TableGameWithWinConditions should be able to keep added win conditions"):
    game.addWinCondition(winCondition)
    game.winConditions should be (List(winCondition))

  test("TableGameWithWinConditions should output winners according to winConditions"):
    _addPlayersToGame(List(winner1, winner2, looser1, looser2))
    game.addWinCondition(winCondition)
    game.winners should be (List(winner1, winner2))

  test("TableGameWithWinConditions should output no winners if there is no winner"):
    _addPlayersToGame(List(looser1, looser2))
    game.addWinCondition(winCondition)
    game.winners should be (List[PlayerModel]())

  test("TableGameWithWinConditions should output no winners if there are no players"):
    game.winners should be (List[PlayerModel]())

  test("TableGameWithWinConditions should output every player as winner if there are no conditions"):
    _addPlayersToGame(List(winner1, winner2, looser1, looser2))
    game.winners should be (List(winner1, winner2, looser1, looser2))
