package org.cge.dsl

import org.cge.engine.Game
import org.cge.engine.Game.SimpleGame

/** A trait that defines a GameBuilder. */
trait GameBuilder:
  /**
   * Sets the name of the game.
   *
   * @param name the name of the game
   * @return the GameBuilder instance
   */
  def setName(name: String): GameBuilder

  /**
   * Builds the game.
   *
   * @return the game
   */
  def build: Game

object GameBuilder:
  export GameBuilder.DSL.* // this let GameBuilder.DSL methods be accessible directly from GameBuilder

  // Global variable to store the current game builder
  private val currentGameBuilder: GameBuilder = GameBuilderImpl()

  private class GameBuilderImpl extends GameBuilder:
    private var _game: Game = SimpleGame("")

    def setName(name: String): this.type =
      this._game = SimpleGame(name)
      this

    def build: Game = this._game

  /** The DSL is meant to be used into a dedicated file so that the final return of that file should be the GameBuilder. */
  object DSL:

    def Game: GameBuilder = currentGameBuilder

    extension (game: GameBuilder)
      infix def is(name: String): GameBuilder =
        game.setName(name)

      // def end: Game = game.build

      // def start: Unit = game.build.startGame()
