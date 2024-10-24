package org.cge.dsl

import org.cge.engine.GameBuilder
import org.cge.dsl.SyntacticSugar.ToSyntacticSugar
import org.cge.dsl.SyntacticSugar.PlayerSyntacticSugar

object SyntacticBuilder:

  /** Companion object for the PlayerBuilder trait. */
  object PlayerBuilder:
    /**
     * This method is used to create a new player builder.
     *
     * @param builder The game builder
     * @return The player builder
     */
    def apply(builder: GameBuilder): PlayerBuilder = new PlayerBuilderImpl(
      builder
    )

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

  /** Companion object for the CountCardBuilder trait. */
  object CountCardBuilder:
    /**
     * This method is used to create a new count card builder.
     *
     * @param builder The game builder
     * @param numOfCards The number of cards
     * @return The count card builder
     */
    def apply(builder: GameBuilder, numOfCards: Int): CountCardBuilder =
      new CountCardBuilderImpl(builder, numOfCards)

  trait CountCardBuilder:
    /**
     * This method is used to set the number of cards in hand for each player.
     *
     * @param numberOfCards The number of cards
     * @return A syntactic sugar builder to complete the sentence
     */
    infix def cards(to: ToSyntacticSugar): CardSyntSugarBuilder

  private class CountCardBuilderImpl(
      val builder: GameBuilder,
      val numOfCards: Int
  ) extends CountCardBuilder:
    infix def cards(to: ToSyntacticSugar): CardSyntSugarBuilder = CardSyntSugarBuilder(
      numOfCards, builder
    )

  /** Companion object for the EachSyntSugarBuilder trait. */
  object CardSyntSugarBuilder:
    /**
     * This method is used to create a new syntactic sugar builder.
     *
     * @param builder The game builder
     * @return The syntactic sugar builder
     */
    def apply(numOfCards: Int, builder: GameBuilder): CardSyntSugarBuilder =
      new CardSyntSugarBuilderImpl(numOfCards, builder)

  /** Syntactic sugar builder to complete the sentence 'game gives 5 cards to each player' */
  trait CardSyntSugarBuilder:
    /**
     * This method is used to complete the count card builder sentence.
     *
     * @param player syntatcic sugar to enable 'game gives 5 cards to each player' syntax
     * @return The game builder
     */
    infix def each(player: PlayerSyntacticSugar): GameBuilder

    /**
      * This method is used to set the number of cards in hand for a specific player.
      *
      * @param playerName The name of the player
      * @return The game builder
      */
    infix def player(playerName: String): GameBuilder

  private class CardSyntSugarBuilderImpl(val numOfCards: Int, val builder: GameBuilder)
      extends CardSyntSugarBuilder:

    infix def player(playerName: String): GameBuilder = 
      builder.cardsInHandPerPlayer(
        computeCards(numOfCards), 
        playerName
      )

    infix def each(player: PlayerSyntacticSugar): GameBuilder = 
      builder.cardsInHand(computeCards(numOfCards))

    private def computeCards(input: Int): () => Int =
      input match
        case -1 => () => 1 + scala.util.Random().nextInt(10)
        case _ => () => input

  /**
   * Syntactic sugar builder to complete the sentence 'game starts from player "Test"'
  */
  trait StarterBuilder:

    /**
     * This method is used to set the player that will start the game.
     *
     * @param player The player that will start the game
     * @return The game builder
     */
    infix def player(player: String): GameBuilder

  object StarterBuilder:
    /**
     * This method is used to create a new starter builder.
     *
     * @param builder The game builder
     * @return The starter builder
     */
    def apply(builder: GameBuilder): StarterBuilder = new StarterBuilderImpl(builder)

    private class StarterBuilderImpl(val builder: GameBuilder) extends StarterBuilder:
      infix def player(player: String): GameBuilder = builder.starterPlayer(player)

