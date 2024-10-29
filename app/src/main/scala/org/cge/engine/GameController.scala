package org.cge.engine

import org.cge.engine.view.monads.Monads.Monad.seqN
import org.cge.engine.view.monads.States.State

import org.cge.engine.view.WindowState
import org.cge.engine.view.WindowState.Window
import org.cge.engine.view.WindowDimensions.*
import org.cge.engine.view.GameView

import org.cge.engine.model.GameModel

/** A controller for the card game engine. */
trait GameController:
  /** Starts the game. */
  def startGame: Unit

object GameController:
  def apply(game: GameModel): GameController = new GameControllerImpl(game)

  private class GameControllerImpl(val game: GameModel) extends GameController:

    val buttonX: Int = 300
    val buttonY: Int = 300
    val buttonWidth: Int = 100
    val buttonHeight: Int = 100

    val tablePlayerName: String = "table"

    def startGame: Unit =
      val gameView = GameView(game.name, windowWidth, windowHeight)

      val initialState = 
        game.players.foldLeft(gameView.addPlayer(tablePlayerName)): (state, player) =>
          player.hand.cards.foldLeft(state.flatMap(_ => gameView.addPlayer(player.name))): (innerState, card) =>
            innerState.flatMap(_ => gameView.addCardToPlayer(player.name, card.rank.toString, card.suit.toString))

      val windowCreation = initialState.flatMap(_ => gameView.show)

      val windowEventsHandling: State[Window, Unit] = for
        events <- windowCreation
        _ <- seqN(events.map(_ => endGame(gameView)))
      yield ()

      windowEventsHandling.run(WindowState.initialWindow)

    def endGame(gameView: GameView): State[Window, Unit] =
      val maxCards = game.players.map(_.hand.cards.size).max
      gameView.endGame(
        game.players.filter(_.hand.cards.size == maxCards).map(_.name)
      )
