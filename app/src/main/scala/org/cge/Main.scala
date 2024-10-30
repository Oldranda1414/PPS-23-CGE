package org.cge

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
    game is "Simple Game"
    game has player called "Player 1"
    game has player called "Player 2"
    game has player called "Player 3"
    game has player called "Player 4"
    game gives 10 cards to each player
    game starts from player "Player 1"
    game suitsAre ("Bastoni", "Denari", "Spade", "Coppe")
    game ranksAre ("Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King")
    game trumpIs "Bastoni"
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
      (g, p) => p.hand.cards.isEmpty
    GameController(game.build).startGame
