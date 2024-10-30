package org.cge

import org.cge.engine.GameController
import org.cge.dsl.CardGameEngineDSL._
import org.cge.dsl.SyntacticSugar.player
import org.cge.dsl.SyntacticSugar.from
import org.cge.dsl.SyntacticSugar.to
import org.cge.dsl.SyntacticSugar.conditions
import org.cge.dsl.SyntacticSugar.rules
// import org.cge.dsl.SyntacticSugar.rules

object Main:

  def main(args: Array[String]): Unit =
    game is "Simple Game"
    game has player called "Player 1"
    game has player called "Player 2"
    game has player called "Player 3"
    game has player called "Player 4"
    game gives 10 cards to each player
    game starts from player "Player 1"
    game suitsAre ("Clubs", "Diamonds", "Hearts", "Spades")
    game ranksAre ("Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King")
    game trumpIs "Spades"
    // game playing rules are:
    //   (table, cardPlayed) => ???
    game hand rules are:
      (hand, cardPlayed, trump, ranks) => 
        val handSuit = hand.head.suit
        val cardSuit = cardPlayed.suit
        val cardRank = cardPlayed.rank
        
        val trumpWinner = hand
          .filter(_.suit == trump)
          .maxByOption(c => ranks.indexOf(c.rank))
        
        if cardSuit == trump && trumpWinner.exists(_.rank == cardRank) then 
          true
        else
          val suitWinner = hand
            .filter(_.suit == handSuit)
            .maxByOption(c => ranks.indexOf(c.rank))
          
          if cardSuit == handSuit && suitWinner.exists(_.rank == cardRank) then 
            true 
          else 
            false

    game win conditions are:
      (g, p) => p.hand.cards.isEmpty
    GameController(game.build).startGame
