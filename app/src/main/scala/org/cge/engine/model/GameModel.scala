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
  def trump_=(suit: Suit): Unit
  def trump: Option[Suit]
  val name: String
  val table: TableModel
  def addWinCondition(winCondition: WinCondition): Unit
  def winConditions: List[WinCondition]
  def winners: List[PlayerModel]

object GameModel:
  def apply(name: String): GameModel = TableGameWithWinConditions(name)

  abstract class SimpleGame(val name: String) extends GameModel:
    var players: List[PlayerModel] = List.empty
    private var turnIndex: Int = 0
    def trump: Option[Suit] = table.trump

    def addPlayer(player: PlayerModel): Unit =
      players = players :+ player
    def removePlayer(player: PlayerModel): Unit =
      players = players.filterNot(_ == player)
    def trump_=(suit: Suit) = 
      table.trump = suit
    def setFirstPlayer(player: PlayerModel): Unit = 
      turnIndex = players.indexOf(player)
    def nextTurn(): Unit =
      turnIndex = (turnIndex + 1) % players.size
    def turn: PlayerModel = players(turnIndex)

  // A WinCondition takes as arguments the current game and a player,
  //   and outputs true if the specified player is a winner.
  type WinCondition = (GameModel, PlayerModel) => Boolean
  class TableGameWithWinConditions(name:String) extends SimpleGame(name):
    val table: TableModel = TableModel()
    var winConditions: List[WinCondition] = List.empty

    def addWinCondition(condition: WinCondition): Unit =
      winConditions = winConditions :+ condition

    def winners: List[PlayerModel] =
      players.filter(p => satisfyEveryWinCondition(p))
    
    private def satisfyEveryWinCondition(player: PlayerModel) =
      winConditions.forall(cond => cond(this, player))




