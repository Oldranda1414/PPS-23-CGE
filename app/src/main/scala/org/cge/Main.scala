package org.cge

import engine.view.WindowStateImpl
import engine.view.Monads.Monad.seqN
import org.cge.engine.GameController
import org.cge.engine.GameBuilder
import scala.util.Random

object Main:

  def main(args: Array[String]): Unit =
    GameController(
      GameBuilder()
        .setName("Simple Game")
        .addPlayer("Andrea")
        .addPlayer("Filippo")
        .addPlayer("Leonardo")
        .cardsInHand(() => 1 + Random().nextInt(9))
        .build
    ).startGame

  // Testing functional GUI with players and cards
  // val windowCreation = for 
  //   // Set the window size
  //   _ <- WindowStateImpl.setSize(600, 600)
    
  //   // Add 4 players to the game
  //   _ <- WindowStateImpl.addPlayer("Player 1")
  //   _ <- WindowStateImpl.addPlayer("Player 2")
  //   _ <- WindowStateImpl.addPlayer("Player 3")
  //   _ <- WindowStateImpl.addPlayer("Player 4")
    
  //   // Add cards to each player
  //   _ <- WindowStateImpl.addCardToPlayer("Player 1", "Ace", "Spades")
  //   _ <- WindowStateImpl.addCardToPlayer("Player 2", "10", "Hearts")
  //   _ <- WindowStateImpl.addCardToPlayer("Player 3", "King", "Diamonds")
  //   _ <- WindowStateImpl.addCardToPlayer("Player 4", "7", "Clubs")
    
  //   // Show the window
  //   _ <- WindowStateImpl.show()
    
  //   // Get the event stream (simulate events if needed)
  //   e <- WindowStateImpl.eventStream()
  // yield e

  // // Handle window events (example handling)
  // val windowEventsHandling = for
  //   e <- windowCreation
  //   // Example event handling for card selection
  //   _ <- seqN(e.map(_ match
  //     case "Player 1" => WindowStateImpl.addCardToPlayer("Player 1", "2", "Clubs") // Simulate event of adding a card
  //     case "Player 2" => WindowStateImpl.addCardToPlayer("Player 2", "Jack", "Diamonds")
  //     case "QuitButton" => WindowStateImpl.exec(sys.exit())
  //   ))
  // yield ()

  // windowEventsHandling.run(WindowStateImpl.initialWindow)
