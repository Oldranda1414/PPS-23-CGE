package org.cge.dsl

import org.cge.engine.model._
import scala.annotation.targetName

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
    def ranks: List[Rank]
    @targetName("applySuit")
    def apply(suits: Suit*): AreSyntacticSugar
    @targetName("applyRank")
    def apply(ranks: Rank*): AreSyntacticSugar

  /** Implementation objects for syntactic sugar */
  private object PlayerSyntacticSugar extends PlayerSyntacticSugar
  private object ToSyntacticSugar extends ToSyntacticSugar
  private object RandomSyntacticSugar extends RandomSyntacticSugar
  private object AreSyntacticSugar extends AreSyntacticSugar:
    var s = Set.empty[Suit]
    var r = List.empty[Rank]

    @targetName("applySuit")
    def apply(suits: Suit*): AreSyntacticSugar = 
      s = suits.toSet
      this

    @targetName("applyRank")
    def apply(ranks: Rank*): AreSyntacticSugar =
      r = ranks.toList
      this

    def suits =
      val ret = s.toSet
      s = s.empty
      ret

    def ranks = 
      val ret = r.toList
      r = r.empty
      ret

