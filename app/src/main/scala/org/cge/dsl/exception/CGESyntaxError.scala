package org.cge.dsl.exception

class CGESyntaxError extends Exception:
  def this(message: String) = 
    this()
    println(message)
