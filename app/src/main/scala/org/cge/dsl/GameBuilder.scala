package org.cge.dsl

import org.cge.engine.Game
import org.cge.engine.Game.SimpleGame

/** A trait that defines a GameBuilder. */
trait GameBuilder:

  /** Resets the builder to its initial state. */
  def reset(): Unit

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
  export DSL.*
  
  private val _gameBuilder: GameBuilder = GameBuilderImpl()

  private class GameBuilderImpl extends GameBuilder:
    private var _gameName: String = ""

    def reset(): Unit = _gameName = ""

    def setName(name: String): this.type =
      require(name.nonEmpty, "Game name cannot be empty")
      require(name.trim.nonEmpty, "Game name cannot be blank")
      require(_gameName.isEmpty, "Game name is already set")
      this._gameName = name
      this

    def build: Game = SimpleGame(this._gameName)

  object DSL:

    def Game: GameBuilder = _gameBuilder

    extension (game: GameBuilder)
      infix def is(name: String): GameBuilder =
        game.setName(name)
