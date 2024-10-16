package org.cge.engine

import org.cge.engine.model.GameModel
import org.cge.engine.model.PlayerModel
import org.cge.engine.model.CardModel
import org.cge.engine.data._
import org.cge.engine.model.Suit

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
   * Adds a player to the game.
   *
   * @param name the name of the player
   * @return the GameBuilder instance
   */
  def addPlayer(name: String): GameBuilder

  /**
    * Sets the number of cards in hand for each player. Since this is a by-need parameter, 
    * it will be evaluated every time it is used allowing for different values to be returned.
    *
    * @param numberOfCards the number of cards in hand
    * @return the GameBuilder instance
    */
  def cardsInHand(numberOfCards: () => Int): GameBuilder

  /**
    * Sets the suits of the game.
    *
    * @param suit the suit to add
    * @return the GameBuilder instance
    */
  def addSuit(suit: Suit): GameBuilder

  /**
   * Builds the game.
   *
   * @return the game
   */
  def build: GameModel

  def currentGameCards: List[CardModel]

object GameBuilder:

  def apply(): GameBuilder = GameBuilderImpl()

  private class GameBuilderImpl extends GameBuilder:
    private var _gameName: String = ""
    private var _players: Set[String] = Set.empty
    private var _cardsInHand: () => Int = () => 0
    private var _availableCards = StandardDeck.cards
    private var _suits = List.empty[Suit]
    private var _executedMethods: Map[String, Boolean] = 
      Map(
        "setName" -> false,
        "addPlayer" -> false,
        "cardsInHand" -> false,
      )

    def setName(name: String): GameBuilder =
      stringRequirements(name, "Game name")
      require(_gameName.isEmpty, "Game name is already set")
      this._gameName = name
      _executedMethods += ("setName" -> true)
      this

    def addPlayer(name: String): GameBuilder =
      stringRequirements(name, "Player name")
      _players.contains(name) match
        case true => throw new IllegalArgumentException(s"Player $name already exists")
        case false => _players = _players + name
      _executedMethods += ("addPlayer" -> true)
      this

    def cardsInHand(numberOfCards: () => Int): GameBuilder = 
      require(numberOfCards() > 0, "Number of cards in hand must be greater than 0")
      require(this._cardsInHand() == 0, "Number of cards in hand is already set")
      this._cardsInHand = numberOfCards
      _executedMethods += ("cardsInHand" -> true)
      this

    def addSuit(suit: Suit): GameBuilder =
      if (_suits.contains(suit)) then throw new IllegalArgumentException(s"Suit $suit already exists")
      _suits = _suits :+ suit
      this

    def currentGameCards: List[CardModel] = computeDeck()

    def build: GameModel = 
      checkExecutedMethods()
      _availableCards = computeDeck()
      
      //create game
      val game = GameModel(this._gameName)
      _players.foreach { name =>
        // create player
        val player = PlayerModel(name)
        game.addPlayer(player)
        for _ <- 1 to _cardsInHand() do
          // populate player's deck
          val card = getRandomAvailableCard()
          player.hand.addCard(card)
          _availableCards = _availableCards.tail
      }
      game

    private def getRandomAvailableCard(): CardModel = 
      val index = scala.util.Random.nextInt(_availableCards.size)
      val card = _availableCards(index)
      _availableCards = _availableCards.patch(index, Nil, 1)
      card

    private def checkExecutedMethods() =
      if _executedMethods.values.exists(_ == false) then throw new IllegalStateException("All methods must be executed")


    private def stringRequirements(s: String, name: String) =
      require(s.nonEmpty, s"$name cannot be empty")
      require(s.trim.nonEmpty, s"$name cannot be blank")

    private def computeDeck(): List[CardModel] = 
      if (_suits.isEmpty) then 
        StandardDeck.cards
      else
        StandardDeck.cards.filter(card => _suits.contains(card.suit))
