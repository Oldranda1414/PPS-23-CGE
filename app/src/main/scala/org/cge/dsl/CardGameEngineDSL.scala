package org.cge.dsl

import org.cge.engine.GameBuilder
import org.cge.dsl.SyntacticSugar._
import org.cge.dsl.SyntacticBuilder.PlayerBuilder
import org.cge.dsl.SyntacticBuilder.CountCardBuilder
import org.cge.engine.model.Suit
import org.cge.engine.model.Rank
import org.cge.dsl.SyntacticBuilder.ConditionsBuilder

object CardGameEngineDSL:

  private var builder = GameBuilder()

  /**
   * This method is used to set the builder for the DSL.
   *
   * @param b The builder to use
   */
  def apply(b: GameBuilder) = builder = b

  /** The starting word for the DSL. It defines the start of a new game */
  implicit def game: GameBuilder = builder

  extension (game: GameBuilder)
    /**
     * This method is used to set the name of the game.
     *
     * @param name The name of the game
     * @return The builder
     */
    infix def is(name: String): GameBuilder = game.setName(name)

    /**
     * This method is used to add a player to the game.
     *
     * @param player syntatcic sugar to enable 'game has player called "blabla"' syntax
     * @return The player builder
     */
    infix def has(syntSugar: PlayerSyntacticSugar): PlayerBuilder = PlayerBuilder(game)

    /**
     * This method is used to set the number of cards in hand for each player.
     *
     * @param numOfCards The number of cards
     * @return The count card builder
     */
    infix def gives(numOfCards: Int): CountCardBuilder = CountCardBuilder(game, numOfCards)

    /**
     * This method is used to set the number of cards in hand for each player.
     *
     * @param random syntactic sugar to enable 'game gives random cards to each player' syntax
     * @return The count card builder
     */
    infix def gives(random: RandomSyntacticSugar): CountCardBuilder = CountCardBuilder(game, -1)

    /**
      * This method is used to define what suits will be available in the game.
      * 
      * @param suits the suits to add
      * @return the GameBuilder instance
      */
    infix def suitsAre(suits: Suit*): GameBuilder = 
      // if (suits.isEmpty) throw new CGESyntaxError("No suits defined") -- not found a way to test this if branch
      suits.foreach(s => game.addSuit(s))
      game

    /**
      * This method is used to define the order of the ranks in the game.
      *
      * @param ranks sorted ranks
      * @return the GameBuilder instance
      */
    infix def ranksAre(ranks: Rank*): GameBuilder =
      // if (ranks.isEmpty) throw new CGESyntaxError("No ranks defined") -- not found a way to test this if branch
      game.addSortedRanks(ranks.toList)

    /**
      * This method is used to set the trump suit for the game.
      *
      * @param suit the trump suit
      * @return the GameBuilder instance
      */
    infix def trumpIs(suit: Suit): GameBuilder = game.setTrump(suit)

    infix def win(conditions: ConditionsSyntacticSugar): ConditionsBuilder = ConditionsBuilder(game)
