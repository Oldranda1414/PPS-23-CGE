package org.cge.engine.model

import org.cge.AnyTest
import org.cge.engine.model.GameModel._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class GameModelTest extends AnyTest with BeforeAndAfterEach:
  private var game: GameModel = SimpleGame("simple game")
  private val testPlayer = PlayerModel("Test")

  override def beforeEach(): Unit =
    game = SimpleGame("simple game")

  test("SimpleGame should be able to add a named player"):
    game.addPlayer(testPlayer)
    game.players should be (List(testPlayer))

  test("SimpleGame should be able to remove a named player"):
    game.addPlayer(testPlayer)
    game.removePlayer(testPlayer)
    game.players should be (List.empty[PlayerModel])

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
    val g = GameModel("name", true)
    g match
      case g: TableGame => g.table mustBe a[TableModel]
      case _ => fail("GameModel was not a TableGame, check apply() function.")
