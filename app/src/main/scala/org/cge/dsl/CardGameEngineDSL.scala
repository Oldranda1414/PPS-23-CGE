package org.cge.dsl

import org.cge.engine.GameBuilder
import org.cge.dsl.SyntacticSugar._
import org.cge.dsl.SyntacticBuilder.PlayerBuilder
import org.cge.dsl.SyntacticBuilder.CountCardBuilder
import org.cge.dsl.exception.CGESyntaxError

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
      * @param are syntactic sugar to enable 'game suits are ( A, B, C, ... )' syntax
      */
    infix def suits(are: AreSyntacticSugar): GameBuilder = 
      println("TESTTTT")
      if (are.suits.isEmpty) throw new CGESyntaxError("No suits defined")
      are.suits.foreach(s => game.addSuit(s))
      game

    infix def ranks(are: AreSyntacticSugar): GameBuilder =
      if (are.ranks.isEmpty) throw new CGESyntaxError("No ranks defined")
      game.addOrderedRanks((are.ranks))
