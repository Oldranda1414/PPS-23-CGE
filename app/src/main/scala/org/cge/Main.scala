package org.cge

import org.cge.engine.GameController
import org.cge.dsl.CardGameEngineDSL._
import org.cge.dsl.SyntacticSugar._
import org.cge.engine.model._
import org.cge.engine.model.GameModel.WinCondition
import org.cge.engine.model.TableModel.HandRule

object Main:

  def main(args: Array[String]): Unit =
    val winCondition1: WinCondition = (game, player) => true || game == player
    val winCondition2: WinCondition = (game, player) => true || game == player

    val handRule1: HandRule = (cardsOnTable, card, trump) =>
      true || cardsOnTable == card
    val handRule2: HandRule = (cardsOnTable, card, trump) =>
      true || cardsOnTable == card

    game is "Simple Game"
    game has player called "Filippo"
    game has player called "Andrea"
    game has player called "Leonardo"
    game gives random cards to player "Leonardo"
    game gives 5 cards to player "Filippo"
    game gives 4 cards to player "Andrea"
    game suitsAre ( "Hearts", "Diamonds", "Clubs", "Spades" )
    game ranksAre ( "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" )
    game trumpIs "Hearts"
    game win conditions are ( winCondition1, winCondition2 )
    game hand rules are ( handRule1, handRule2 )
    GameController(game.build).startGame
