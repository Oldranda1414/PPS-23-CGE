package org.cge.dsl

import org.cge.engine.GameBuilder

/**
 * DSL for the card game engine.
 * It will be used to create card games in a more readable way.
 */
trait CardGameEngineDSL:
    val todo: Unit = ???

object CardGameEngineDSL:
  implicit val game: GameBuilder = GameBuilder()
