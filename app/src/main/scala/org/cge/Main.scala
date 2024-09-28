package org.cge

import engine.*

object Main:
  def main(args: Array[String]): Unit =
    val card1 = SimpleCard("1", "spades")
    val card2 = SimpleCard("2", "spades")
    val card3 = SimpleCard("3", "spades")

  def greeting(message: String = "Hello, world!"): String = message
