package org.cge.engine.view

import org.cge.engine.view.monads.States.State
import org.cge.engine.view.monads.Streams.Stream
import org.cge.engine.view.SwingFunctionalFacade.Frame

/** A view for card engine game. */
trait GameView:
  /** Shows the game. */
  def show: State[Frame, Stream[String]]

  /** Adds a player to the game view. */
  def addPlayer(name: String): State[Frame, Unit]

  /** Adds a card to a player. */
  def addCardToPlayer(player: String, cardValue: String, cardSuit: String): State[Frame, Unit]

  /** Removes a card from a player. */
  def removeCardFromPlayer(player: String, cardValue: String, cardSuit: String): State[Frame, Unit]

  /** Adds a button to the GUI. */
  def addButton(text: String, name: String, x: Int, y: Int, width: Int, height: Int): State[Frame, Unit]

  /** Ends the game, displaying the winners. */
  def endGame(winners: List[String]): State[Frame, Unit]

object GameView:
  def apply(gameName: String, width: Int, height: Int): GameView = new GameViewImpl(gameName, width, height)

  private class GameViewImpl(val gameName: String, width: Int, height: Int) extends GameView:

    // Static configurations for winner labels
    private val singleWinnerLabelPrefix: String = "The winner is "
    private val multiWinnerLabelPrefix: String = "The winners are "
    private val winnerLabelX = 200
    private val winnerLabelY = 300
    private val winnerLabelWidth = 500
    private val winnerLabelHeight = 100

    private val initialWindowCreation: State[Frame, Unit] = 
      WindowState.setSize(width, height)

    override def show: State[Frame, Stream[String]] =
      for
        _ <- initialWindowCreation
        _ <- WindowState.show()
        e <- WindowState.eventStream()
      yield e

    override def addPlayer(name: String): State[Frame, Unit] =
      PlayerViewManager.addPlayer(initialWindowCreation, name)

    override def addCardToPlayer(player: String, cardValue: String, cardSuit: String): State[Frame, Unit] =
      CardViewManager.addCardToPlayer(initialWindowCreation, player, cardValue, cardSuit)

    override def removeCardFromPlayer(player: String, cardValue: String, cardSuit: String): State[Frame, Unit] =
      CardViewManager.removeCardFromPlayer(initialWindowCreation, player, cardValue, cardSuit)

    override def addButton(text: String, name: String, x: Int, y: Int, width: Int, height: Int): State[Frame, Unit] =
      ButtonViewManager.addButton(initialWindowCreation, name, text, x, y, width, height)

    def endGame(winners: List[String]) =
      if winners.size == 0 then
        throw new IllegalArgumentException("No winners provided.")
      else if winners.size == 1 then
        displayWinner(singleWinnerLabelPrefix + winners.head)
      else
        displayWinner(multiWinnerLabelPrefix + winners.mkString(", "))

    private def displayWinner(winnerText: String): State[Frame, Unit] =
      WindowState.addLabel(winnerText, winnerLabelX, winnerLabelY, winnerLabelWidth, winnerLabelHeight)
