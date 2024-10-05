package org.cge

import engine.view.WindowStateImpl
import engine.view.Monads.Monad.seqN
import org.cge.engine.GameController
import org.cge.engine.GameBuilder

object Main:

  def main(args: Array[String]): Unit =
    GameController(
      GameBuilder()
        .setName("Simple Game")
        .addPlayer("Andrea")
        .addPlayer("Filippo")
        .addPlayer("Leonardo")
        .cardsInHand(5)
        .build
    ).startGame

    // Testing functional GUI with players and cards
    val windowCreation = for 
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

    val windowEventsHandling = for
      e <- windowCreation
      _ <- seqN(e.map(_ match
        case "QuitButton" => WindowStateImpl.exec(sys.exit())
      ))
    yield ()

    windowEventsHandling.run(WindowStateImpl.initialWindow)
