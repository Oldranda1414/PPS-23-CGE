package org.cge

import org.cge.engine.GameController
import org.cge.dsl.CardGameEngineDSL._
import org.cge.dsl.SyntacticSugar._
import org.cge.engine.model._

object Main:

  def main(args: Array[String]): Unit =
    game is "Simple Game"
    game suitsAre ( "Rods", "Coins", "Swords", "Cups" )
    game ranksAre ( "3", "4", "5", "6", "7", "Knave", "Knight", "King", "Ace", "2" )
    game has player called "Filippo"
    game has player called "Andrea"
    game has player called "Leonardo"
    game has player called "Giuseppe"
    game gives 10 cards to player "Leonardo"
    game gives 10 cards to player "Filippo"
    game gives 10 cards to player "Andrea"
    game gives 10 cards to player "Giuseppe"
    game starts from random player
    GameController(game.build).startGame
