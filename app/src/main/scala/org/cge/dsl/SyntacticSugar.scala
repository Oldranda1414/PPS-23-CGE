package org.cge.dsl

import org.cge.engine.model.Suit
import org.cge.engine.model.Rank

/** Base trait for syntactic sugar */
trait SyntacticSugar

object SyntacticSugar:

  /**
    * Syntactic sugar to make the DSL more readable.
    * It is used to enable 'game suitsAre StandardSuits' syntax
    */
  object StandardSuits extends Suit

  /**
    * Syntactic sugar to make the DSL more readable.
    * It is used to enable 'game ranksAre StandardRanks' syntax
    */
  object StandardRanks extends Rank

  /**
   * Syntactic sugar to make the DSL more readable.
   * It is used to enable 'game has player called "blabla" ' and
   * ' game gives 5 cards to each player' syntaxes
   */
  implicit val player: PlayerSyntacticSugar = PlayerSyntacticSugar

  /**
   * Syntactic sugar to make the DSL more readable.
   * It is used to enable 'game gives 5 cards to each player'
   */
  implicit val to: ToSyntacticSugar = ToSyntacticSugar

  /**
   * Syntactic sugar to make the DSL more readable.
   * It is used to enable 'game gives random cards to each player'
   */
  implicit val random: RandomSyntacticSugar = RandomSyntacticSugar

  /** Specific syntactic sugar for player */
  trait PlayerSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for to */
  trait ToSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for random */
  trait RandomSyntacticSugar extends SyntacticSugar

  /** Implementation objects for syntactic sugar */
  private object PlayerSyntacticSugar extends PlayerSyntacticSugar
  private object ToSyntacticSugar extends ToSyntacticSugar
  private object RandomSyntacticSugar extends RandomSyntacticSugar
