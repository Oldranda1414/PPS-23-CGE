package org.cge.engine.view

import org.cge.engine.view.monads.States.State
import org.cge.engine.view.monads.Streams.Stream
import org.cge.engine.view.SwingFunctionalFacade.Frame

/** A view for card engine game. */
trait GameView:
  /** Shows the game. */
  def show: State[Frame, Stream[String]]

  /**
    * Add a player to the game view
    *
    * @param name the player's name
    */
  def addPlayer(name: String): Unit

  /**
    * Adds a card to a player
    *
    * @param player the name of the player to add the card to
    * @param cardValue the value of the card
    * @param cardSuit the suit of the card
    */
  def addCardToPlayer(player: String, cardValue: String, cardSuit: String): Unit

  /**
    * Adds a button to the gui
    *
    * @param text the text to appear in the button
    * @param name the name of the event the button will trigger
    * @param x the x position of the button
    * @param y the y position of the button
    * @param width the width of the button
    * @param height the height of the button
    */
  def addButton(text: String, name: String, x: Int, y: Int, width: Int, height: Int): Unit

  /**
   * Ends the game displaying the winners.
   *
   * @param winners the winners of the game
   */
  def endGame(winners: List[String]): State[Frame, Unit]

object GameView:
  def apply(gameName: String, width: Int, height: Int): GameView = new GameViewImpl(gameName, width, height)

  private class GameViewImpl(val gameName: String, width: Int, height: Int) extends GameView:

    val singleWinnerLabelPrefix: String = "The winner is "
    val multiWinnerLabelPrefix: String = "The winners are "
    val winnerLabelX = 200
    val winnerLabelY = 300
    val winnerLabelWidth = 500
    val winnerLabelHeight = 100

    var windowCreation: State[Frame, Unit] =
      for
        _ <- WindowState.setSize(width, height)
      yield ()

    def show: State[Frame, Stream[String]] =
      for
        _ <- windowCreation
        _ <- WindowState.show()
        e <- WindowState.eventStream()
      yield e

    def addPlayer(name: String): Unit = 
      windowCreation = 
        PlayerViewManager.addPlayer(windowCreation, name)
    
    def addCardToPlayer(player: String, cardValue: String, cardSuit: String): Unit =
      windowCreation = 
        CardViewManager.addCardToPlayer(windowCreation, player, cardValue, cardSuit)
    
    def addButton(text: String, name: String, x: Int, y: Int, width: Int, height: Int): Unit =
      windowCreation = 
        ButtonViewManager.addButton(windowCreation, name, text, x, y, width, height)

    def endGame(winners: List[String]) =
      if winners.size == 0 then
        throw new IllegalArgumentException("No winners provided.")
      else if winners.size == 1 then
        displayWinner(singleWinnerLabelPrefix + winners.head)
      else
        displayWinner(multiWinnerLabelPrefix + winners.mkString(", "))
    
    def displayWinner(winnerText: String) =
      for _ <- WindowState.addLabel(winnerText, winnerLabelX, winnerLabelY, winnerLabelWidth, winnerLabelHeight) yield ()
