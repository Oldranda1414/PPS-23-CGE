package org.cge

import org.cge.engine.GameController
import org.cge.dsl.CardGameEngineDSL._
import org.cge.dsl.SyntacticSugar._
import org.cge.engine.model._

object Main:

  def main(args: Array[String]): Unit =
    game is "Simple Game"
    game has player called "Filippo"
    game has player called "Andrea"
    game has player called "Leonardo"
    game gives random cards to each player
    game suitsAre ( "Hearts", "Diamonds", "Clubs", "Spades" )
    game ranksAre ( "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" )
    game trumpIs "Hearts"
    GameController(game.build).startGame
