package org.cge

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
