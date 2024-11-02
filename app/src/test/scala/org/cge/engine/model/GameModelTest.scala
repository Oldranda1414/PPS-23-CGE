package org.cge.engine.model

import org.cge.AnyTest
import org.cge.engine.model.GameModel._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.BeforeAndAfterEach

import scala.language.implicitConversions

class GameModelTest extends AnyTest with BeforeAndAfterEach:
  private val gameName = "Test"
  private var game: GameModel = GameModel(gameName)
  private val testPlayer = PlayerModel("Test")
  private val winner1 = PlayerModel("winner1")
  private val winner2 = PlayerModel("winner2")
  private val looser1 = PlayerModel("looser1")
  private val looser2 = PlayerModel("looser2")
  private val card1: CardModel = CardModel("Ace", "Spades")
  private val card2: CardModel = CardModel("2", "Spades")
  private val card3: CardModel = CardModel("3", "Clubs")
  private val winCondition: WinCondition = (game, player) => player.name.contains("winner")
  private val playingRule: PlayingRule = (table: TableModel, player: PlayerModel, card: CardModel) =>
    table.cardsOnTable.filterNot(card => card.suit == Suit("Spades")).size == 0
      && card.suit == Suit("Spades")

  private def addPlayersToGame(players: List[PlayerModel]) =
    players.foreach(game.addPlayer(_))

  override def beforeEach(): Unit =
    game = GameModel(gameName)

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
    game.name should be (gameName)

  test("GameModel should have a table"):
      game.table mustBe a[TableModel]

  test("TableGameWithWinConditions should start with an empty list of win conditions"):
    game.winConditions should be (List[WinCondition]())

  test("TableGameWithWinConditions should be able to keep added win conditions"):
    game.addWinCondition(winCondition)
    game.winConditions should be (List(winCondition))

  test("TableGameWithWinConditions should output winners according to winConditions"):
    addPlayersToGame(List(winner1, winner2, looser1, looser2))
    game.addWinCondition(winCondition)
    game.winners should be (List(winner1, winner2))

  test("TableGameWithWinConditions should output no winners if there is no winner"):
    addPlayersToGame(List(looser1, looser2))
    game.addWinCondition(winCondition)
    game.winners should be (List[PlayerModel]())

  test("TableGameWithWinConditions should output no winners if there are no players"):
    game.winners should be (List[PlayerModel]())

  test("TableGameWithWinConditions should output every player as winner if there are no conditions"):
    addPlayersToGame(List(winner1, winner2, looser1, looser2))
    game.winners should be (List(winner1, winner2, looser1, looser2))

  test("Next turn increases the turn"):
    addPlayersToGame(List(winner1, winner2, looser1, looser2))
    game.nextTurn()
    game.turn should be (winner2)

  test("Next turn rounds robin when at the end of the list"):
    addPlayersToGame(List(winner1, winner2, looser1, looser2))
    game.nextTurn()
    game.nextTurn()
    game.nextTurn()
    game.nextTurn()
    game.turn should be (winner1)

  test("In ModelModel it is possible to add playing rules"):
    game.addPlayingRule(playingRule)
    game.playingRules should be (List(playingRule))

  test("GameModel should mark a playable card as playable"):
    game.addPlayingRule(playingRule)
    winner1.hand.addCard(card2)
    game.canPlayCard(winner1, card2) should be (true)
    winner1.hand.removeCard(card2)

  test("GameModel should mark a non-playable card as non-playable"):
    game.addPlayingRule(playingRule)
    winner1.hand.addCard(card3)
    game.canPlayCard(winner1, card3) should be (false)
    winner1.hand.removeCard(card3)

  test("GameModel should accept playing cards following the added rules"):
    game.addPlayingRule(playingRule)
    game.table.playCard(card1)
    game.table.playCard(card2)
    game.table.cardsOnTable should be (List(card1, card2))
