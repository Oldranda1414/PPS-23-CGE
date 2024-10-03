package org.cge.dsl

import org.cge.engine.Game
import org.cge.engine.Game.SimpleGame

trait GameBuilder:
  def setName(name: String): GameBuilder
  def build: Game

object GameBuilder:
  export GameBuilder.DSL.* // this let GameBuilder.DSL methods be accessible directly from GameBuilder

  def configure(configuration: GameBuilder ?=> GameBuilder): GameBuilder =
    given GameBuilder = GameBuilderImpl()
    configuration

  private class GameBuilderImpl extends GameBuilder:
    private var _game: Game = SimpleGame("")

    def setName(name: String): this.type =
      this._game = SimpleGame(name)
      this

    def build: Game = this._game

  object DSL:
    def Game(using game: GameBuilder): GameBuilder = game

    def is(using game: GameBuilder, name: String): GameBuilder =
      game.setName(name)
