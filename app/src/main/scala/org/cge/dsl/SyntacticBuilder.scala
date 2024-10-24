package org.cge.dsl

import org.cge.engine.GameBuilder
import org.cge.dsl.SyntacticSugar.ToSyntacticSugar
import org.cge.dsl.SyntacticSugar.PlayerSyntacticSugar
import org.cge.engine.model.GameModel.WinCondition

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
    infix def cards(to: ToSyntacticSugar): EachSyntSugarBuilder

  private class CountCardBuilderImpl(
      val builder: GameBuilder,
      val numOfCards: Int
  ) extends CountCardBuilder:
    infix def cards(to: ToSyntacticSugar): EachSyntSugarBuilder =
      numOfCards match
        case -1 =>
          new EachSyntSugarImpl(
            builder.cardsInHand(() => 1 + scala.util.Random.nextInt(10))
          )
        case _ => new EachSyntSugarImpl(builder.cardsInHand(() => numOfCards))

  /** Companion object for the EachSyntSugarBuilder trait. */
  object EachSyntSugarBuilder:
    /**
     * This method is used to create a new syntactic sugar builder.
     *
     * @param builder The game builder
     * @return The syntactic sugar builder
     */
    def apply(builder: GameBuilder): EachSyntSugarBuilder =
      new EachSyntSugarImpl(builder)

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

  object ConditionsBuilder:
    def apply(builder: GameBuilder): ConditionsBuilder =
      new ConditionsBuilderImpl(builder)

  trait ConditionsBuilder:
    infix def are(winConditions: WinCondition*): GameBuilder

  private class ConditionsBuilderImpl(val builder: GameBuilder)
      extends ConditionsBuilder:
    infix def are(winConditions: WinCondition*): GameBuilder =
      winConditions.foreach(builder.addWinCondition(_))
      builder
