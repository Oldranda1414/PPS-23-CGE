package org.cge.engine

import org.cge.engine.view.GameView
import org.cge.engine.view.Monads.Monad.seqN
import org.cge.engine.view.WindowStateImpl
import org.cge.engine.model.GameModel

/** A controller for card game engine. */
trait GameController:
  /** Starts the game. */
  def startGame: Unit

object GameController:
  def apply(game: GameModel): GameController = new GameControllerImpl(game)

  private class GameControllerImpl(val game: GameModel) extends GameController:

    def startGame: Unit =
      val gameView = GameView(game.name, 1000, 1000)

      game.players.foreach: player =>
        gameView.addPlayer(player.name)
        player.hand.cards.foreach: card =>
          gameView.addCardToPlayer(player.name, card.value, card.suit)
      
      gameView.addButton("Results", "EndGame")

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
      val maxCards = game.players.map(_.hand.cards.size).max
      gameView.endGame(
        game.players.filter(_.hand.cards.size == maxCards).map(_.name)
      )
