package org.example

object app:
  def main(args: Array[String]): Unit =
    println(greeting()) // works after making sure parentheses are used

  def greeting(message: String = "Hello, world!"): String = message
