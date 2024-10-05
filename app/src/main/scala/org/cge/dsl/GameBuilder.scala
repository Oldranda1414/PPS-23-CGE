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
   * Adds a player to the game.
   *
   * @param name the name of the player
   * @return the GameBuilder instance
   */
  def addPlayer(name: String): GameBuilder

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
    private var _players: Set[String] = Set.empty

    def reset(): Unit = 
      _gameName = ""
      _players = _players.empty

    def setName(name: String): this.type =
      stringRequirements(name, "Game name")
      require(_gameName.isEmpty, "Game name is already set")
      this._gameName = name
      this

    def addPlayer(name: String): this.type =
      stringRequirements(name, "Player name")
      _players.contains(name) match
        case true => throw new IllegalArgumentException(s"Player $name already exists")
        case false => _players = _players + name
      this

    def build: Game = 
      val game = SimpleGame(this._gameName)
      reset()
      game

    private def stringRequirements(s: String, name: String) =
      require(s.nonEmpty, s"$name cannot be empty")
      require(s.trim.nonEmpty, s"$name cannot be blank")

  object PlayerCardsBuilder:
    def method: Unit = ()

  object DSL:

    def Game: GameBuilder = _gameBuilder

    extension (game: GameBuilder)
      infix def is(name: String): GameBuilder =
        game.setName(name)

      infix def playersHave(numberOfCards: Int = -1): PlayerCardsBuilder =
        ???
