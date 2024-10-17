package org.cge.engine.model

import org.cge.AnyTest
import org.cge.engine.model.GameModel._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

class GameModelTest extends AnyTest with BeforeAndAfterEach:
  private var gameModel: GameModel = SimpleGame("simple game")
  private val testPlayer = PlayerModel("Test")

  override def beforeEach(): Unit =
    gameModel = SimpleGame("simple game")

  test("SimpleGame should be able to add a named player"):
    gameModel.addPlayer(testPlayer)
    gameModel.players should be (List(testPlayer))

  test("SimpleGame should be able to remove a named player"):
    gameModel.addPlayer(testPlayer)
    gameModel.removePlayer(testPlayer)
    gameModel.players should be (List.empty[PlayerModel])

  test("SimpleGame should be able to add multiple named players"):
    val testPlayer2: PlayerModel = PlayerModel("Test2")

    gameModel.addPlayer(testPlayer)
    gameModel.addPlayer(testPlayer2)
    gameModel.players should be (List(testPlayer, testPlayer2))

  test("SimpleGame should have initialized name"):
    gameModel.name should be ("simple game")

  test("TableGame should have a table"):
    val g = GameModel("name", true)
    g match
      case g: TableGame => g.table mustBe a[TableModel]
      case _ => fail("GameModel was not a TableGame, check apply() function.")
