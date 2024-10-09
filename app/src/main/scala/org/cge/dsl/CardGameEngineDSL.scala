package org.cge.dsl

import org.cge.engine.GameBuilder

object CardGameEngineDSL:

  private var builder = GameBuilder() 

  /**
    * This method is used to set the builder for the DSL.
    *
    * @param b The builder to use
    */
  def apply(b: GameBuilder) = builder = b

  /**
   * The starting word for the DSL. It defines the start of a new game
   */
  implicit def game: GameBuilder = builder

  /**
    * This class is used to build a player in the DSL.
    *
    * @param builder The builder to use
    */
  class PlayerBuilder(val builder: GameBuilder):
    val player: CalledBuilder = new CalledBuilder(builder)

  class CalledBuilder(val builder: GameBuilder):
    infix def called(name: String): GameBuilder = builder.addPlayer(name)

  extension (game: GameBuilder)
    /**
      * This method is used to set the name of the game.
      *
      * @param name The name of the game
      * @return The builder
      */
    infix def is(name: String): GameBuilder = game.setName(name)
    infix def has: PlayerBuilder = new PlayerBuilder(game)
