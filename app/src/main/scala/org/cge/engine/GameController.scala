package org.cge.engine

/** A controller for card game engine. */
trait GameController:
  /** Starts the game. */
  def startGame: Unit

object GameController:
  def apply(game: Game): GameController = new GameControllerImpl(game)

  private class GameControllerImpl(val game: Game) extends GameController:

    def startGame: Unit =
      println(s"Starting game ${game.name} ...")
      println(s"Player: ${game.players.maxBy(_.deck.cards.size).name} has won ${game.name} game!")


