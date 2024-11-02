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

  /** Clears a player's hand. */
  def clearPlayerHand(player: String): State[Frame, Unit]

  /** Adds a button to the GUI. */
  def addButton(text: String, name: String, x: Int, y: Int, width: Int, height: Int): State[Frame, Unit]

  /** Ends the game, displaying the winners. */
  def endGame(winners: List[String]): State[Frame, Unit]

object GameView:
  def apply(gameName: String, width: Int, height: Int): GameView = new GameViewImpl(gameName, width, height)

  private class GameViewImpl(val gameName: String, width: Int, height: Int) extends GameView:

    private val singleWinnerLabelPrefix: String = "The winner is "
    private val multiWinnerLabelPrefix: String = "The winners are "
    private val winnerLabelX = 200
    private val winnerLabelY = 300
    private val winnerLabelWidth = 500
    private val winnerLabelHeight = 100

    private val initialWindowCreation: State[Frame, Unit] = 
      WindowState.setSize(width, height)

    /** Shows the game. */
    override def show: State[Frame, Stream[String]] =
      for
        _ <- initialWindowCreation
        _ <- WindowState.show()
        e <- WindowState.eventStream()
      yield e

    /** Adds a player to the game view. */
    override def addPlayer(name: String): State[Frame, Unit] =
      PlayerViewManager.addPlayer(name)

    /** Adds a card to a player. */
    override def addCardToPlayer(player: String, cardValue: String, cardSuit: String): State[Frame, Unit] =
      CardViewManager.addCardToPlayer(player, cardValue, cardSuit)

    /** Removes a card from a player. */
    override def removeCardFromPlayer(player: String, cardValue: String, cardSuit: String): State[Frame, Unit] =
      CardViewManager.removeCardFromPlayer(player, cardValue, cardSuit)

    /** Clears a player's hand. */
    override def clearPlayerHand(player: String): State[Frame, Unit] =
      CardViewManager.clearPlayerHand(player)

    /**
      * Adds a button to the GUI.
      *
      * @param text the text of the button
      * @param name
      * @param x
      * @param y
      * @param width
      * @param height
      */
    override def addButton(text: String, name: String, x: Int, y: Int, width: Int, height: Int): State[Frame, Unit] =
      ButtonViewManager.addButton(name, text, x, y, width, height)

    /**
     * Ends the game, displaying the winners. 
     * 
     * @param winners a list of the names of the winning players
     * @return the updated state of GUI
     */
    def endGame(winners: List[String]) =
      if winners.size == 0 then
        throw new IllegalArgumentException("No winners provided.")
      else if winners.size == 1 then
        displayWinner(singleWinnerLabelPrefix + winners.head)
      else
        displayWinner(multiWinnerLabelPrefix + winners.mkString(", "))

    private def displayWinner(winnerText: String): State[Frame, Unit] =
      WindowState.addLabel(winnerText, winnerLabelX, winnerLabelY, winnerLabelWidth, winnerLabelHeight)
