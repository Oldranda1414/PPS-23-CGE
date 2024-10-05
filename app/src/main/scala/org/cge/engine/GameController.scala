package org.cge.engine

import org.cge.engine.view.GameView
import org.cge.engine.view.Monads.Monad.seqN
import org.cge.engine.view.WindowStateImpl

/** A controller for card game engine. */
trait GameController:
  /** Starts the game. */
  def startGame: Unit

object GameController:
  def apply(game: Game): GameController = new GameControllerImpl(game)

  private class GameControllerImpl(val game: Game) extends GameController:

    def startGame: Unit =
      val gameView = GameView(game.name)
      val windowCreation = gameView.show
      val windowEventsHandling = for
        e <- windowCreation
        _ <- seqN(e.map(_ match
          case "QuitButton" => WindowStateImpl.exec(sys.exit())
          case "EndGame" => endGame(gameView)
        ))
      yield ()

      windowEventsHandling.run(WindowStateImpl.initialWindow)

    def endGame(gameView: GameView) =
      val maxCards = game.players.map(_.deck.cards.size).max
      gameView.endGame(
        game.players.filter(_.deck.cards.size == maxCards).map(_.name)
      )
