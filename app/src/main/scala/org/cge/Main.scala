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
    object MySuit extends Suit
    game suitsAre ( Hearts, Clubs, MySuit )
    object MyRank extends Rank
    game ranksAre ( MyRank, Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King )
    game trumpIs Hearts
    GameController(game.build).startGame
