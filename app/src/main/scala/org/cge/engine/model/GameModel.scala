package org.cge.engine.model

import org.cge.engine._
import org.cge.engine.model.GameModel.WinCondition

trait GameModel:
  def players: List[PlayerModel]
  def addPlayer(player: PlayerModel): Unit
  def removePlayer(player: PlayerModel): Unit
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
    var trump: Option[Suit] = None

    def addPlayer(player: PlayerModel): Unit =
      players = players :+ player
    def removePlayer(player: PlayerModel): Unit =
      players = players.filterNot(_ == player)
    def trump_=(suit: Suit) = 
      trump = Some(suit)

  // A WinCondition takes as arguments the current game and a player,
  //   and outputs true if the specified player is a winner.
  type WinCondition = (GameModel, PlayerModel) => Boolean
  class TableGameWithWinConditions(name:String) extends SimpleGame(name):
    val table: TableModel = TableModel()
    var winConditions: List[WinCondition] = List.empty

    def addWinCondition(condition: WinCondition): Unit =
      winConditions = winConditions :+ condition

    def winners: List[PlayerModel] =
      players.filter(p => _satisfyEveryWinCondition(p))
    
    private def _satisfyEveryWinCondition(player: PlayerModel) =
      winConditions.forall(cond => cond(this, player))




