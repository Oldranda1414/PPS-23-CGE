package org.cge

import org.cge.engine.GameController
import org.cge.engine.GameBuilder
import scala.util.Random

object Main:

  def main(args: Array[String]): Unit =
    GameController(
      GameBuilder()
        .setName("Simple Game")
        .addPlayer("Player 1")
        .addPlayer("Player 2")
        .addPlayer("Player 3")
        .addPlayer("Player 4")
        .cardsInHand(() => 1 + Random().nextInt(9))
        .build
    ).startGame
