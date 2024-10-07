package org.cge.engine.view

import org.cge.engine.view.States.State
import org.cge.engine.view.SwingFunctionalFacade.Frame

/** A view for card engine game. */
trait GameView:
  /** Shows the game. */
  def show: State[Frame, Streams.Stream[String]]

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
   * Ends the game displaying the winners.
   *
   * @param winners the winners of the game
   */
  def endGame(winners: List[String]): State[Frame, Unit]

object GameView:
  def apply(gameName: String, width: Int, height: Int): GameView = new GameViewImpl(gameName, width, height)

  private class GameViewImpl(val gameName: String, width: Int, height: Int) extends GameView:

    var windowCreation: State[Frame, Unit] =
      for
        _ <- WindowStateImpl.setSize(width, height)
      yield ()

    def show: State[Frame, Streams.Stream[String]] =
      for
        _ <- windowCreation
        _ <- WindowStateImpl.show()
        e <- WindowStateImpl.eventStream()
      yield e

    def addPlayer(name: String): Unit = 
      windowCreation = 
        for
          _ <- windowCreation
          _ <- WindowStateImpl.addPlayer(name)
        yield ()
    
    def addCardToPlayer(player: String, cardValue: String, cardSuit: String): Unit =
      windowCreation = 
        for
          _ <- windowCreation
          _ <- WindowStateImpl.addCardToPlayer(player, cardValue, cardSuit)
        yield ()

    def endGame(winners: List[String]) =
      if winners.size == 0 then
        throw new IllegalArgumentException("No winners provided.")
      else if winners.size == 1 then
        for _ <- WindowStateImpl.displayWinner(winners.head) yield ()
      else
        for _ <- WindowStateImpl.displayWinner(winners.mkString(", ")) yield ()
