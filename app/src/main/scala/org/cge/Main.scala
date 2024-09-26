package org.cge

object Main:
  def main(args: Array[String]): Unit =
    println(greeting())

  def greeting(message: String = "Hello, world!"): String = message
