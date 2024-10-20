package org.cge.dsl.exception

/**
  * This class is used to represent a syntax error in the DSL.
  */
class CGESyntaxError(message: String) extends Exception():
  println(message)
