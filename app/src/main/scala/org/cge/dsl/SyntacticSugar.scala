package org.cge.dsl

import org.cge.engine.model._

/** Base trait for syntactic sugar */
trait SyntacticSugar

object SyntacticSugar:

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

  /**
   * Syntactic sugar to make the DSL more readable.
   * It is used to enable 'game suits are A, B, C ' syntax
   */
  implicit val are: AreSyntacticSugar = AreSyntacticSugar

  /** Specific syntactic sugar for player */
  trait PlayerSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for to */
  trait ToSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for random */
  trait RandomSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for suits */
  trait AreSyntacticSugar extends SyntacticSugar:
    def suits: Set[Suit]
    def apply(suits: Suit*): AreSyntacticSugar

  /** Implementation objects for syntactic sugar */
  private object PlayerSyntacticSugar extends PlayerSyntacticSugar
  private object ToSyntacticSugar extends ToSyntacticSugar
  private object RandomSyntacticSugar extends RandomSyntacticSugar
  private object AreSyntacticSugar extends AreSyntacticSugar:
    var s = Set.empty[Suit]
    def apply(suits: Suit*): AreSyntacticSugar = 
      s = suits.toSet
      this

    def suits = s
