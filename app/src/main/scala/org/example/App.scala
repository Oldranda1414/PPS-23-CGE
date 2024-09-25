package org.example

object app:
  def main(args: Array[String]): Unit =
    println(greeting())

  def greeting(message: String = "Hello, world!"): String = message
