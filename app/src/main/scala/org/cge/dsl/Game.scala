package org.cge.dsl

object GameBuilder:
  export GameBuilder.DSL.*

  trait GameBuilder:
    def setName(name: String): GameBuilder
  
  class GameBuilderImpl extends GameBuilder:
    private var name: String = ""

    def setName(name: String): this.type =
      this.name = name
      this

  object DSL:
    def Game(using game: GameBuilder): GameBuilder = game

    def is(using game: GameBuilder, name: String): GameBuilder = game.setName(name)
