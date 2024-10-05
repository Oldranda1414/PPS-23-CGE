package org.cge.engine

import org.cge.engine.Game.SimpleGame
import org.cge.engine.data.StandardDeck
import org.cge.engine.Game.SimplePlayer

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
    * Sets the number of cards in hand for each player.
    *
    * @param numberOfCards the number of cards in hand
    * @return the GameBuilder instance
    */
  def cardsInHand(numberOfCards: Int): GameBuilder

  /**
   * Builds the game.
   *
   * @return the game
   */
  def build: Game

object GameBuilder:

  def apply(): GameBuilder = GameBuilderImpl()

  private class GameBuilderImpl extends GameBuilder:
    private var _gameName: String = ""
    private var _players: Set[String] = Set.empty
    private var _cardsInHand: Int = 0
    private var _availableCards = StandardDeck.cards

    def reset(): Unit = 
      _gameName = ""
      _players = _players.empty
      _cardsInHand = 0
      _availableCards = StandardDeck.cards

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

    def cardsInHand(numberOfCards: Int): GameBuilder = 
      require(numberOfCards > 0, "Number of cards in hand must be greater than 0")
      require(this._cardsInHand == 0, "Number of cards in hand is already set")
      this._cardsInHand = numberOfCards
      this

    def build: Game = 
      //create game
      val game = SimpleGame(this._gameName)
      _players.foreach { name =>
        // create player
        val player = SimplePlayer(name)
        game.addPlayer(player)
        for _ <- 1 to _cardsInHand do
          // populate player's deck
          val card = _availableCards.head
          player.deck.addCard(card)
          _availableCards = _availableCards.tail
      }
      reset()
      game

    private def stringRequirements(s: String, name: String) =
      require(s.nonEmpty, s"$name cannot be empty")
      require(s.trim.nonEmpty, s"$name cannot be blank")
