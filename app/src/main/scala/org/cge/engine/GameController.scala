package org.cge.engine

import org.cge.engine.view.GameView

/** A controller for card game engine. */
trait GameController:
  /** Starts the game. */
  def startGame: Unit

object GameController:
  def apply(game: Game): GameController = new GameControllerImpl(game)

  private class GameControllerImpl(val game: Game) extends GameController:  

    def startGame: Unit =
      val gameView = GameView(game.name)
      gameView.show
      val maxCards = game.players.map(_.deck.cards.size).max
      gameView.endGame(game.players.filter(_.deck.cards.size == maxCards).map(_.name))


