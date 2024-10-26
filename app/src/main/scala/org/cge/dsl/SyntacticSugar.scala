package org.cge.dsl

import org.cge.dsl.SyntacticSugar.RulesSyntacticSugar

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
   * It is used to enable 'game win conditions are (...)'
   */
  implicit val conditions: ConditionsSyntacticSugar = ConditionsSyntacticSugar

  /**
   * Syntactic sugar to make the DSL more readable.
   * It is used to enable 'game hand rules are (...)'
   */
  implicit val rules: RulesSyntacticSugar = RulesSyntacticSugar

  /** Specific syntactic sugar for player */
  trait PlayerSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for to */
  trait ToSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for random */
  trait RandomSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for conditions */
  trait ConditionsSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for rules */
  trait RulesSyntacticSugar extends SyntacticSugar

  /** Implementation objects for syntactic sugar */
  private object PlayerSyntacticSugar extends PlayerSyntacticSugar
  private object ToSyntacticSugar extends ToSyntacticSugar
  private object RandomSyntacticSugar extends RandomSyntacticSugar
  private object ConditionsSyntacticSugar extends ConditionsSyntacticSugar
  private object RulesSyntacticSugar extends RulesSyntacticSugar
