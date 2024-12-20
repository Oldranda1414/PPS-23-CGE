package org.cge

import scala.language.implicitConversions

import org.cge.engine.GameController
import org.cge.dsl.CardGameEngineDSL._
import org.cge.dsl.SyntacticSugar.player
import org.cge.dsl.SyntacticSugar.from
import org.cge.dsl.SyntacticSugar.to
import org.cge.dsl.SyntacticSugar.conditions
import org.cge.dsl.SyntacticSugar.rules
import org.cge.engine.model.TableModel.HandRule

object Main:

  def main(args: Array[String]): Unit =
    game is "Marafone"
    game has player called "Player 1"
    game has player called "Player 2"
    game has player called "Player 3"
    game has player called "Player 4"
    game gives 10 cards to each player
    game starts from player "Player 1"
    game suitsAre ("Batons", "Coins", "Cups", "Swords")
    game ranksAre ("Seven", "Six", "Five", "Four", "Knave", "Knight", "King", "Ace", "Two", "Three")
    game trumpIs "Batons"
    game playing rules are:
      (table, player, playerCard) => 
        player.hand.cards.contains(playerCard) &&
        (table.cardsOnTable.isEmpty || 
        (table.cardsOnTable.head.suit == playerCard.suit || player.hand.cards.filter(_.suit == table.cardsOnTable.head.suit).isEmpty))
        
    game hand rules are:
      (hand, cardPlayed, trump, ranks) => 
        val handSuit = hand.head.suit
        val t = trump.getOrElse("")
        val trumpsInHand = hand.filter(_.suit == t)
        val leadingSuitsInHand = hand.filter(_.suit == handSuit)
        val winningCard = if (trumpsInHand.nonEmpty) then
          trumpsInHand.maxBy(c => ranks.indexOf(c.rank))
        else
          leadingSuitsInHand.maxBy(c => ranks.indexOf(c.rank))
        cardPlayed == winningCard
    game win conditions are:
      (g, p) => 
        val nordSudCouple = List(g.players(0), g.players(2))
        val eastWestCouple = List(g.players(1), g.players(3))
        val playerCouple = if nordSudCouple.contains(p) then nordSudCouple else eastWestCouple
        val enemyCouple = if nordSudCouple.contains(p) then eastWestCouple else nordSudCouple
        playerCouple.map(_.points).sum > enemyCouple.map(_.points).sum
    GameController(game.build).startGame
