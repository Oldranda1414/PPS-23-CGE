package org.cge.engine

import org.cge.engine.model.GameModel
import org.cge.engine.model.PlayerModel
import org.cge.engine.model.CardModel
import org.cge.engine.data._
import org.cge.engine.model.Suit
import org.cge.engine.model.Rank

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
    * It's mutually exclusive with cardsInHandPerPlayer method.
    * @param numberOfCards the number of cards in hand
    * @return the GameBuilder instance
    */
  def cardsInHand(numberOfCards: () => Int): GameBuilder

  /**
    * Sets the number of cards in hand to the passed player.
    * It's mutually exclusive with cardsInHands method.
    *
    * @param numberOfCards the number of cards in hand
    * @param player the player to set the number of cards in hand
    * @return the GameBuilder instance
    */
  def cardsInHandPerPlayer(numberOfCards: () => Int, player: String): GameBuilder

  /**
    * Sets the suits of the game.
    *
    * @param suit the suit to add
    * @return the GameBuilder instance
    */
  def addSuit(suit: Suit): GameBuilder

  /**
   * Adds a list of sorted ranks to the game. It needs to be sorted to decide which rank is higher.
   * 
   * @param suits the suits to add
   * @return the GameBuilder instance
   * 
    */
  def addSortedRanks(ranks: List[Rank]): GameBuilder

  /**
    * Sets the trump suit of the game.
    *
    * @param suit the trump suit
    * @return the GameBuilder instance
    */
  def setTrump(suit: Suit): GameBuilder

  /**
    * Sets the player that will start the game.
    *
    * @param player the player that will start the game
    * @return the GameBuilder instance
    */
  def starterPlayer(player: String): GameBuilder

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
    private var _suits = Set.empty[Suit]
    private var _ranks = List.empty[Rank]
    private var _trump: Option[Suit] = None
    private var _starterPlayer: String = ""
    private var _cardsInHandPerPlayer: Map[String, () => Int] = Map.empty
    private var _isCardsInHandCalled = false;
    private var _executedMethods: Map[String, Boolean] = 
      Map(
        "setName" -> false,
        "addPlayer" -> false,
        "addSuit" -> false,
        "addSortedRanks" -> false
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
      require(_cardsInHandPerPlayer.isEmpty, "Cannot call cardsInHand after cardsInHandPerPlayer")
      this._cardsInHand = numberOfCards
      _isCardsInHandCalled = true
      this

    def cardsInHandPerPlayer(numberOfCards: () => Int, player: String): GameBuilder = 
      require(numberOfCards() > 0, "Number of cards in hand must be greater than 0")
      require(!_cardsInHandPerPlayer.contains(player), s"Number of cards in hand for player $player is already set")
      require(_players.contains(player), s"Player $player does not exist")
      require(!_isCardsInHandCalled, "Cannot call cardsInHandPerPlayer after cardsInHand")
      _cardsInHandPerPlayer = _cardsInHandPerPlayer + (player -> numberOfCards)
      this
    
    def addSuit(suit: Suit): GameBuilder =
      require(!_suits.contains(suit), s"Suit $suit already exists")
      _executedMethods += ("addSuit" -> true)
      _suits = _suits + suit
      this

    def addSortedRanks(ranks: List[Rank]): GameBuilder = 
      require(ranks.nonEmpty, "Ranks cannot be empty")
      require(_ranks.isEmpty, "Ranks are already set")
      _executedMethods += ("addSortedRanks" -> true)
      _ranks = ranks
      this

    def setTrump(suit: Suit): GameBuilder =
      _trump = Some(suit)
      this

    def starterPlayer(player: String) = 
      _starterPlayer = player
      this

    def currentGameCards: List[CardModel] = computeDeck()

    def build: GameModel = 
      checkExecutedMethods()
      _availableCards = computeDeck()
      //create game
      val game = GameModel(this._gameName)
      _trump match
        case Some(suit) => 
          require(_suits.contains(suit), s"Cannot set $suit as trump as it is not a suit in the game")
          game.trump = suit
        case None => ()
      _players.foreach { name =>
        // create player
        val player = PlayerModel(name)
        game.addPlayer(player)
        setPlayerCards(player)
      }
      game

    private def getRandomAvailableCard(): CardModel = 
      require(_availableCards.nonEmpty, "No cards available")
      val index = scala.util.Random.nextInt(_availableCards.size)
      val card = _availableCards(index)
      _availableCards = _availableCards.patch(index, Nil, 1)
      card

    private def checkExecutedMethods() =
      if _executedMethods.values.exists(_ == false) then throw new IllegalStateException("All methods must be executed")
      if !_isCardsInHandCalled && _cardsInHandPerPlayer.keySet != _players then throw new IllegalStateException("All players must have a number of cards in hand")

    private def stringRequirements(s: String, name: String) =
      require(s.nonEmpty, s"$name cannot be empty")
      require(s.trim.nonEmpty, s"$name cannot be blank")

    private def computeDeck(): List[CardModel] = 
      if (_suits.nonEmpty && _ranks.nonEmpty) then 
        for
          suit <- _suits.toList
          rank <- _ranks
        yield CardModel(rank, suit)
      else List.empty

    private def setPlayerCards(player: PlayerModel) =
      def populateHand(cardInHand: () => Int) = 
        for _ <- 1 to cardInHand() do
          player.hand.addCard(getRandomAvailableCard())
      
      if _cardsInHandPerPlayer.nonEmpty then
        _cardsInHandPerPlayer.foreach { case (name, cardsInHand) =>
          if player.name == name then
            populateHand(cardsInHand)
        }
      else 
        populateHand(_cardsInHand)