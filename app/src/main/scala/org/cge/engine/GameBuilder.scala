package org.cge.engine

import org.cge.engine.model.GameModel
import org.cge.engine.model.PlayerModel
import org.cge.engine.model.CardModel
import org.cge.engine.data._
import org.cge.engine.model.Suit
import org.cge.engine.model.Rank
import org.cge.engine.model.TableModel.PlayingRule
import org.cge.engine.model.GameModel.WinCondition
import org.cge.engine.model.TableModel.HandRule
import org.cge.engine.model.TableModel

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
   * Adds a playing rule to the game.
   * 
   * @param rule the playing rule to add
   * @return the GameBuilder instance
  */
  def addPlayingRule(rule: PlayingRule): GameBuilder
  /**
    * Adds a win condition to the game.
    *
    * @param winCondition
    * @return
    */
  def addWinCondition(winCondition: WinCondition): GameBuilder

  /**
    * Adds a hand rule to the game.
    *
    * @param handRule
    * @return
    */
  def addHandRule(handRule: HandRule): GameBuilder

  /**
   * Builds the game.
   *
   * @return the game
   */
  def build: GameModel

  def currentGameCards: List[CardModel]

  def currentPlayers: List[PlayerModel]

object GameBuilder:

  def apply(): GameBuilder = GameBuilderImpl()

  private class GameBuilderImpl extends GameBuilder:
    private var gameName: String = ""
    private var players: List[String] = List.empty
    private var cardsInHand: () => Int = () => 0
    private var availableCards = StandardDeck.cards
    private var suits = Set.empty[Suit]
    private var ranks = List.empty[Rank]
    private val table = TableModel()
    private var trump: Option[Suit] = None
    private var starterPlayer: String = ""
    private var winConditions = List.empty[WinCondition]
    private var cardsInHandPerPlayer: Map[String, () => Int] = Map.empty
    private var isCardsInHandCalled = false;
    private var tableRules: List[PlayingRule] = List.empty
    private var executedMethods: Map[String, Boolean] = 
      Map(
        "setName" -> false,
        "addPlayer" -> false,
        "addSuit" -> false,
        "addSortedRanks" -> false
      )

    def setName(name: String): GameBuilder =
      stringRequirements(name, "Game name")
      require(gameName.isEmpty, "Game name is already set")
      this.gameName = name
      executedMethods += ("setName" -> true)
      this

    def addPlayer(name: String): GameBuilder =
      stringRequirements(name, "Player name")
      players.contains(name) match
        case true => throw new IllegalArgumentException(s"Player $name already exists")
        case false => players = players :+ name
      executedMethods += ("addPlayer" -> true)
      this

    def cardsInHand(numberOfCards: () => Int): GameBuilder = 
      require(numberOfCards() > 0, "Number of cards in hand must be greater than 0")
      require(this.cardsInHand() == 0, "Number of cards in hand is already set")
      require(cardsInHandPerPlayer.isEmpty, "Cannot call cardsInHand after cardsInHandPerPlayer")
      this.cardsInHand = numberOfCards
      isCardsInHandCalled = true
      this

    def cardsInHandPerPlayer(numberOfCards: () => Int, player: String): GameBuilder = 
      require(numberOfCards() > 0, "Number of cards in hand must be greater than 0")
      require(!cardsInHandPerPlayer.contains(player), s"Number of cards in hand for player $player is already set")
      require(players.contains(player), s"Player $player does not exist")
      require(!isCardsInHandCalled, "Cannot call cardsInHandPerPlayer after cardsInHand")
      cardsInHandPerPlayer = cardsInHandPerPlayer + (player -> numberOfCards)
      this
    
    def addSuit(suit: Suit): GameBuilder =
      require(!suits.contains(suit), s"Suit $suit already exists")
      executedMethods += ("addSuit" -> true)
      suits = suits + suit
      this

    def addSortedRanks(ranks: List[Rank]): GameBuilder = 
      require(ranks.nonEmpty, "Ranks cannot be empty")
      require(this.ranks.isEmpty, "Ranks are already set")
      executedMethods += ("addSortedRanks" -> true)
      this.ranks = ranks
      this

    def setTrump(suit: Suit): GameBuilder =
      require(trump.isEmpty, "Trump is already set")
      trump = Some(suit)
      this

    def starterPlayer(player: String) = 
      require(players.contains(player), s"Player $player does not exist")
      require(starterPlayer == "", "Starter player is already set")
      starterPlayer = player
      this

    def addPlayingRule(rule: PlayingRule) =
      tableRules = tableRules :+ rule
      this
    def addWinCondition(winCondition: WinCondition): GameBuilder =
      winConditions = winConditions :+ winCondition
      this

    def addHandRule(handRule: HandRule): GameBuilder =
      table.addHandRule(handRule)
      this

    def currentGameCards: List[CardModel] = computeDeck()

    def currentPlayers: List[PlayerModel] =
      players.map(PlayerModel(_))

    def build: GameModel = 
      checkExecutedMethods()
      availableCards = computeDeck()
      //create game
      val game = GameModel(this.gameName)
      trump match
        case Some(suit) => 
          require(suits.contains(suit), s"Cannot set $suit as trump as it is not a suit in the game")
          game.trump = suit
        case None => ()

      tableRules.foreach(game.table.addPlayingRule)
      availableCards.foreach(game.table.deck.addCard)
      table.handRules.foreach(game.table.addHandRule)
      players.foreach { name =>
        // create player
        val player = PlayerModel(name)
        game.addPlayer(player)
        if starterPlayer == name then game.setFirstPlayer(player)
        setPlayerCards(player)
      }
      winConditions.foreach(game.addWinCondition)
      game

    private def getRandomAvailableCard(): CardModel = 
      require(availableCards.nonEmpty, "No cards available")
      val index = scala.util.Random.nextInt(availableCards.size)
      val card = availableCards(index)
      availableCards = availableCards.patch(index, Nil, 1)
      card

    private def checkExecutedMethods() =
      if executedMethods.values.exists(_ == false) then throw new IllegalStateException("All methods must be executed")
      if !isCardsInHandCalled && cardsInHandPerPlayer.keySet != players.toSet then throw new IllegalStateException("All players must have a number of cards in hand")

    private def stringRequirements(s: String, name: String) =
      require(s.nonEmpty, s"$name cannot be empty")
      require(s.trim.nonEmpty, s"$name cannot be blank")

    private def computeDeck(): List[CardModel] = 
      if (suits.nonEmpty && ranks.nonEmpty) then 
        for
          suit <- suits.toList
          rank <- ranks
        yield CardModel(rank, suit)
      else List.empty

    private def setPlayerCards(player: PlayerModel) =
      def populateHand(cardInHand: () => Int) = 
        for _ <- 1 to cardInHand() do
          player.hand.addCard(getRandomAvailableCard())
      
      if cardsInHandPerPlayer.nonEmpty then
        cardsInHandPerPlayer.foreach { case (name, cardsInHand) =>
          if player.name == name then
            populateHand(cardsInHand)
        }
      else 
        populateHand(cardsInHand)