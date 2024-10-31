package org.cge.engine

import org.cge.engine.view.monads.Monads.Monad.seqN
import org.cge.engine.view.monads.States.State

import org.cge.engine.view.WindowState
import org.cge.engine.view.WindowState.Window
import org.cge.engine.view.WindowDimensions.*
import org.cge.engine.view.GameView

import org.cge.engine.model.GameModel
import org.cge.engine.model.CardModel
import org.cge.engine.model.PlayerModel
import org.cge.engine.model.Rank
import org.cge.engine.model.Suit

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

    val gameView: GameView = GameView(game.name, windowWidth, windowHeight)

    private var turnCounter: Int = 0

    def startGame: Unit =

      val initialState = 
        for 
          _ <- gameView.addPlayer(tablePlayerName)
          _ <- game.players.foldLeft(doNothing(): State[Window, Unit]):
            (state, player) =>
              for
                _ <- state
                _ <- gameView.addPlayer(player.name)
                _ <- player.hand.cards.foldLeft(doNothing(): State[Window, Unit]):
                  (innerState, card) =>
                    for
                      _ <- innerState
                      _ <- gameView.addCardToPlayer(player.name, card.rank.toString, card.suit.toString)
                    yield ()
              yield ()
        yield ()


      val windowCreation = initialState.flatMap(_ => gameView.show)

      val windowEventsHandling: State[Window, Unit] = for
        events <- windowCreation
        _ <- seqN(events.map(e =>
          val parsedEvent = e.split(":")
          val eventName = parsedEvent(0)
          eventName match
            case s if game.players.map(_.name).contains(eventName) =>
              handleCardPlayed(s, parsedEvent(1))
            case _ =>
              doNothing()
        ))
      yield ()

      windowEventsHandling.run(WindowState.initialWindow)

    private def doNothing(): State[Window, Unit] = State(s => (s, ()))

    private def handleCardPlayed(
        playerName: String,
        cardFromEvent: String
    ): State[Window, Unit] =
      val rank = cardFromEvent.split(" ")(0)
      val suit = cardFromEvent.split(" ")(1)
      val card = CardModel(rank, suit)
      game.players.find(_.name == playerName) match
        case Some(player) =>
          if game.turn.name != playerName || !game.canPlayCard(game.turn, card) then
            doNothing()
          else playCard(player, card)
        case None =>
          throw new NoSuchElementException(s"Player $playerName not found")

    private def playCard(player: PlayerModel, card: CardModel) =
      turnCounter += 1
      game.playCard(player, card)
      if turnCounter == game.players.size then
        turnCounter = 0
        moveCardToTable(player, card).flatMap(_ =>
          endHand())
      else moveCardToTable(player, card)
  
    private def endHand(): State[Window, Unit] =
      game.computeHandEnd()
      gameView.clearPlayerHand(tablePlayerName)

    private def moveCardToTable(player: PlayerModel, card: CardModel): State[Window, Unit] =
      val rank = card.rank.toString
      val suit = card.suit.toString

      for
        _ <- gameView.removeCardFromPlayer(player.name, rank, suit)
        _ <- gameView.addCardToPlayer(tablePlayerName, rank, suit)
        _ <- if game.players.forall(_.hand.cards.isEmpty) then
              if game.winners.nonEmpty then
                gameView.endGame(game.winners.map(_.name))
              else
                gameView.endGame(List("no one :P"))
        else
          doNothing()
      yield ()
