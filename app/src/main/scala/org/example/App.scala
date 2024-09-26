package org.example

object App:
  def main(args: Array[String]): Unit =
    println(greeting())

  def greeting(message: String = "Hello, world!"): String = message
