package org.cge.dsl

import org.cge.engine.GameBuilder
import org.cge.dsl.SyntacticSugar._

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

  /** This class is used to build a player in the DSL. */
  trait PlayerBuilder:
    /**
     * This method is used to set the name of the player.
     *
     * @param name The name of the player
     * @return The builder
     */
    infix def called(name: String): GameBuilder

  private class PlayerBuilderImpl(val builder: GameBuilder)
      extends PlayerBuilder:
    infix def called(name: String): GameBuilder = builder.addPlayer(name)

  trait CountCardBuilder:
    /**
     * This method is used to set the number of cards in hand for each player.
     *
     * @param numberOfCards The number of cards
     * @return A syntactic sugar builder to complete the sentence
     */
    infix def cards(to: ToSyntacticSugar): EachSyntSugarBuilder

  private class CountCardBuilderImpl(
      val builder: GameBuilder,
      val numOfCards: Int
  ) extends CountCardBuilder:
    infix def cards(to: ToSyntacticSugar): EachSyntSugarBuilder =
      numOfCards match
        case -1 =>
          new EachSyntSugarImpl(
            builder.cardsInHand(() => scala.util.Random.nextInt(10))
          )
        case _ => new EachSyntSugarImpl(builder.cardsInHand(() => numOfCards))

  /** Syntactic sugar builder to complete the sentence 'game gives 5 cards to each player' */
  trait EachSyntSugarBuilder:
    /**
     * This method is used to complete the count card builder sentence.
     *
     * @param player syntatcic sugar to enable 'game gives 5 cards to each player' syntax
     * @return The game builder
     */
    infix def each(player: PlayerSyntacticSugar): GameBuilder

  private class EachSyntSugarImpl(val builder: GameBuilder)
      extends EachSyntSugarBuilder:
    infix def each(player: PlayerSyntacticSugar): GameBuilder = builder

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
    infix def has(syntSugar: PlayerSyntacticSugar): PlayerBuilder =
      new PlayerBuilderImpl(game)

    /**
     * This method is used to set the number of cards in hand for each player.
     *
     * @param numOfCards The number of cards
     * @return The count card builder
     */
    infix def gives(numOfCards: Int): CountCardBuilder =
      new CountCardBuilderImpl(game, numOfCards)

    /**
     * This method is used to set the number of cards in hand for each player.
     *
     * @param random syntatcic sugar to enable 'game gives random cards to each player' syntax
     * @return The count card builder
     */
    infix def gives(random: RandomSyntacticSugar): CountCardBuilder =
      new CountCardBuilderImpl(game, -1)
