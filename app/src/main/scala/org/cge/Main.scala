package org.cge

import org.cge.engine.GameController
import org.cge.dsl.CardGameEngineDSL._
import org.cge.dsl.SyntacticSugar.player
import org.cge.dsl.SyntacticSugar.from
import org.cge.dsl.SyntacticSugar.to

object Main:

  def main(args: Array[String]): Unit =
    game is "Simple Game"
    game has player called "Player 1"
    game has player called "Player 2"
    game has player called "Player 3"
    game has player called "Player 4"
    game gives 5 cards to each player
    game starts from player "Player 1"
    game suitsAre ("Clubs", "Diamonds", "Hearts", "Spades")
    game ranksAre ("Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King")
    GameController(game.build).startGame
