package org.cge.engine

import org.cge.engine.view.GameView
import org.cge.engine.view.monads.Monads.Monad.seqN
import org.cge.engine.view.WindowStateImpl
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

    def startGame: Unit =
      val gameView = GameView(game.name, windowWidth, windowHeight)

      game.players.foreach: player =>
        gameView.addPlayer(player.name)
        player.deck.cards.foreach: card =>
          gameView.addCardToPlayer(player.name, card.value, card.suit)
      
      gameView.addButton("Results", "EndGame", buttonX, buttonY, buttonWidth, buttonHeight)

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
