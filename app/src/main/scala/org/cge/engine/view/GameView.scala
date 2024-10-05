package org.cge.engine.view

/** A view for card engine game. */
trait GameView:
  /** Shows the game. */
  def show: Unit

  /**
    * Ends the game displaying the winners.
    *
    * @param winners the winners of the game
    */
  def endGame(winners: List[String]): Unit

object GameView:
  def apply(gameName: String): GameView = new GameViewImpl(gameName)

  private class GameViewImpl(val gameName: String) extends GameView:
    def show: Unit =
      println(s"Game $gameName has started ...")

    def endGame(winners: List[String]) =
      if winners.size == 0 then throw new IllegalArgumentException("No winners provided.")
      else if winners.size == 1 then println(s"Game $gameName has ended. The winner is ${winners.head}.")
      else println(s"Game $gameName has ended in a draw between ${winners.mkString(" and ")}.")
