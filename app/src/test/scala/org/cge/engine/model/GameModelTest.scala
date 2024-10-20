package org.cge.engine.model

import org.cge.AnyTest
import org.cge.engine.model.GameModel._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class GameModelTest extends AnyTest with BeforeAndAfterEach:
  private var game: GameModel = SimpleGame("simple game")
  private var tableGame = GameModel("table game", true)
  private val testPlayer = PlayerModel("Test")
  private val winner1 = PlayerModel("winner1")
  private val winner2 = PlayerModel("winner2")
  private val looser1 = PlayerModel("looser1")
  private val looser2 = PlayerModel("looser2")
  private val winCondition: WinCondition =
    (game, player) => player.name.contains("winner")

  private def _addPlayersToTableGame(players: List[PlayerModel]) =
    players.foreach(tableGame.addPlayer(_))

  override def beforeEach(): Unit =
    game = GameModel("simple game")
    tableGame = GameModel("table game", true)

  test("SimpleGame should be able to add a named player"):
    game.addPlayer(testPlayer)
    game.players should be (List(testPlayer))

  test("SimpleGame should be able to remove a named player"):
    game.addPlayer(testPlayer)
    game.removePlayer(testPlayer)
    game.players should be (List[PlayerModel]())

  test("SimpleGame should be able to add multiple named players"):
    val testPlayer2: PlayerModel = PlayerModel("Test2")

    game.addPlayer(testPlayer)
    game.addPlayer(testPlayer2)
    game.players should be (List(testPlayer, testPlayer2))

  test("SimpleGame should have initialized name"):
    game.name should be ("simple game")

  test("SimpleGame trump should be empty after initializazion"):
    game.trump should be (None)

  test("SimpleGame trump should change after setting it"):
    game.trump = Spades
    game.trump should be (Some(Spades))

  test("TableGame should have a table"):
    tableGame match
      case g: TableGameWithWinConditions => g.table mustBe a[TableModel]
      case _ => fail("GameModel was not a TableGame, check apply() function.")

  test("TableGameWithWinConditions should start with an empty list of win conditions"):
    tableGame match
      case g: TableGameWithWinConditions =>
        g.winConditions should be (List[WinCondition]())
      case _ => fail("GameModel was not a TableGameWithWinConditions, check apply() function.")

  test("TableGameWithWinConditions should be able to keep added win conditions"):
    tableGame match
      case g: TableGameWithWinConditions =>
        g.addWinCondition(winCondition)
        g.winConditions should be (List(winCondition))
      case _ => fail("GameModel was not a TableGameWithWinConditions, check apply() function.")

  test("TableGameWithWinConditions should output winners according to winConditions"):
    _addPlayersToTableGame(List(winner1, winner2, looser1, looser2))
    tableGame match
      case g: TableGameWithWinConditions =>
        g.addWinCondition(winCondition)
        g.winners should be (List(winner1, winner2))
      case _ => fail("GameModel was not a TableGameWithWinConditions, check apply() function.")

  test("TableGameWithWinConditions should output no winners if there is no winner"):
    _addPlayersToTableGame(List(looser1, looser2))
    tableGame match
      case g: TableGameWithWinConditions =>
        g.addWinCondition(winCondition)
        g.winners should be (List[PlayerModel]())
      case _ => fail("GameModel was not a TableGameWithWinConditions, check apply() function.")

  test("TableGameWithWinConditions should output no winners if there are no players"):
    tableGame match
      case g: TableGameWithWinConditions =>
        g.addWinCondition(winCondition)
        g.winners should be (List[PlayerModel]())
      case _ => fail("GameModel was not a TableGameWithWinConditions, check apply() function.")

  test("TableGameWithWinConditions should output every player as winner if there are no conditions"):
    _addPlayersToTableGame(List(winner1, winner2, looser1, looser2))
    tableGame match
      case g: TableGameWithWinConditions =>
        g.winners should be (List(winner1, winner2, looser1, looser2))
      case _ => fail("GameModel was not a TableGameWithWinConditions, check apply() function.")
