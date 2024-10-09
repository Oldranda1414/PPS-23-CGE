package org.cge.dsl

import org.cge.engine.GameBuilder

object CardGameEngineDSL:

  private var builder = GameBuilder() 

  def apply(b: GameBuilder) = builder = b

  /**
   * The starting word for the DSL. It defines the start of a new game
   */
  implicit def game: GameBuilder = builder

  extension (game: GameBuilder)
    infix def is(name: String): GameBuilder = game.setName(name)