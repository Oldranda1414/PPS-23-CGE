package org.cge.engine.view

import org.cge.engine.view.States.State
import org.cge.engine.view.SwingFunctionalFacade.Frame

/** A view for card engine game. */
trait GameView:
  /** Shows the game. */
  def show: State[Frame, Streams.Stream[String]]

  /**
   * Ends the game displaying the winners.
   *
   * @param winners the winners of the game
   */
  def endGame(winners: List[String]): State[Frame, Unit]

object GameView:
  def apply(gameName: String): GameView = new GameViewImpl(gameName)

  private class GameViewImpl(val gameName: String) extends GameView:
    def show: State[Frame, Streams.Stream[String]] =
      for
        _ <- WindowStateImpl.setSize(1000, 1000)

        _ <- WindowStateImpl.addPlayer("Player 1")
        _ <- WindowStateImpl.addPlayer("Player 2")
        _ <- WindowStateImpl.addPlayer("Player 3")
        _ <- WindowStateImpl.addPlayer("Player 4")

        _ <- WindowStateImpl.addCardToPlayer("Player 1", "Ace", "Spades")
        _ <- WindowStateImpl.addCardToPlayer("Player 2", "10", "Hearts")
        _ <- WindowStateImpl.addCardToPlayer("Player 3", "King", "Diamonds")
        _ <- WindowStateImpl.addCardToPlayer("Player 4", "7", "Clubs")

        _ <- WindowStateImpl.show()

        e <- WindowStateImpl.eventStream()
      yield e

    def endGame(winners: List[String]) =
      if winners.size == 0 then
        throw new IllegalArgumentException("No winners provided.")
      else if winners.size == 1 then
        for _ <- WindowStateImpl.displayWinner(winners.head) yield ()
      else
        for _ <- WindowStateImpl.displayWinner(winners.mkString(", ")) yield ()
