package org.cge

import org.cge.engine.GameController
import org.cge.dsl.CardGameEngineDSL._
import org.cge.dsl.SyntacticSugar._

object Main:

  def main(args: Array[String]): Unit =
    game is "Simple Game"
    game has player called "Filippo"
    game has player called "Andrea"
    game has player called "Leonardo"
    game gives random cards to each player
    GameController(game.build).startGame
