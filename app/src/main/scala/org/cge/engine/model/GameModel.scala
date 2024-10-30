package org.cge.engine.model

import org.cge.engine._
import org.cge.engine.model.GameModel.WinCondition

trait GameModel:
  def players: List[PlayerModel]
  def addPlayer(player: PlayerModel): Unit
  def removePlayer(player: PlayerModel): Unit
  def setFirstPlayer(player: PlayerModel): Unit
  def turn: PlayerModel
  def nextTurn(): Unit
  def setTurn(player: PlayerModel): Unit
  val name: String
  val table: TableModel
  def addWinCondition(winCondition: WinCondition): Unit
  def winConditions: List[WinCondition]
  def winners: List[PlayerModel]
  def playCard(player: PlayerModel, card: CardModel): Boolean
  def computeHandEnd(): Unit

object GameModel:
  def apply(name: String): GameModel = TableGameWithWinConditions(name)

  abstract class SimpleGame(val name: String) extends GameModel:
    var players: List[PlayerModel] = List.empty
    private var turnIndex: Int = 0
    def addPlayer(player: PlayerModel): Unit =
      players = players :+ player
    def removePlayer(player: PlayerModel): Unit =
      players = players.filterNot(_ == player)
    def setFirstPlayer(player: PlayerModel): Unit = 
      turnIndex = players.indexOf(player)
    def nextTurn(): Unit =
      turnIndex = (turnIndex + 1) % players.size
    def turn: PlayerModel = players(turnIndex)

    def setTurn(player: PlayerModel): Unit =
      require(players.contains(player), "Player must be in the game")
      turnIndex = players.indexOf(player)

    def playCard(player: PlayerModel, card: CardModel): Boolean = 
      if player == turn && player.hand.cards.contains(card) then
        table.playCard(card)
        player.playCard(card)
        nextTurn()
        true
      else false

  // A WinCondition takes as arguments the current game and a player,
  //   and outputs true if the specified player is a winner.
  type WinCondition = (GameModel, PlayerModel) => Boolean
  class TableGameWithWinConditions(name:String) extends SimpleGame(name):
    val table: TableModel = TableModel()
    var winConditions: List[WinCondition] = List.empty

    override def playCard(player: PlayerModel, card: CardModel): Boolean = 
      if table.canPlayCard(card) then super.playCard(player, card)
      else false

    def computeHandEnd(): Unit = 
      val winningCard = table.cardsOnTable.find(table.doesCardWinHand)
      println(s"winningCard: $winningCard")
      winningCard match
        case Some(card) => 
          val handWinner = players.find(_.playedCards.cards.contains(card))
          handWinner match
            case Some(player) => 
              player.points += 1
              setTurn(player)
              table.takeCards()
            case None => throw new IllegalStateException("No player has played the winning card")
        case None => throw new IllegalStateException("No card has won the hand")

    def addWinCondition(condition: WinCondition): Unit =
      winConditions = winConditions :+ condition

    def winners: List[PlayerModel] =
      players.filter(p => satisfyEveryWinCondition(p))
    
    private def satisfyEveryWinCondition(player: PlayerModel) =
      winConditions.forall(cond => cond(this, player))




