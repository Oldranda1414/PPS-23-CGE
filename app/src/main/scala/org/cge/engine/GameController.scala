package org.cge.engine

import org.cge.engine.view.GameView
import org.cge.engine.view.monads.Monads.Monad.seqN
import org.cge.engine.view.WindowState
import org.cge.engine.model.GameModel

object WindowDimentions:
  val windowWidth: Int = 1000
  val windowHeight: Int = 1000

/** A controller for card game engine. */
trait GameController:
  /** Starts the game. */
  def startGame: Unit

object GameController:
  def apply(game: GameModel): GameController = new GameControllerImpl(game)

  private class GameControllerImpl(val game: GameModel) extends GameController:

    import WindowDimentions.*

    val buttonX: Int = 300
    val buttonY: Int = 300
    val buttonWidth: Int = 100
    val buttonHeight: Int = 100

    val tablePlayerName: String = "table"

    def startGame: Unit =
      val gameView = GameView(game.name, windowWidth, windowHeight)

      gameView.addPlayer(tablePlayerName)

      game.players.foreach: player =>
        gameView.addPlayer(player.name)
        player.hand.cards.foreach: card =>
          gameView.addCardToPlayer(player.name, card.rank.toString(), card.suit.toString())
      
      val windowCreation = gameView.show
      val windowEventsHandling = for
        e <- windowCreation
        _ <- seqN(e.map(_ match
          case _ => endGame(gameView)
        ))
      yield ()

      windowEventsHandling.run(WindowState.initialWindow)

    def endGame(gameView: GameView) =
      val maxCards = game.players.map(_.hand.cards.size).max
      gameView.endGame(
        game.players.filter(_.hand.cards.size == maxCards).map(_.name)
      )
