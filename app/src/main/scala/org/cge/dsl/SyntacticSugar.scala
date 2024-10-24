package org.cge.dsl

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
   * It is used to enable 'game starts from player "Test"'
   */
  implicit val from: FromSyntacticSugar = FromSyntacticSugar

  /** Specific syntactic sugar for player */
  trait PlayerSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for to */
  trait ToSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for random */
  trait RandomSyntacticSugar extends SyntacticSugar

  /** Specific syntactic sugar for with */
  trait FromSyntacticSugar extends SyntacticSugar

  /** Implementation objects for syntactic sugar */
  private object PlayerSyntacticSugar extends PlayerSyntacticSugar
  private object ToSyntacticSugar extends ToSyntacticSugar
  private object RandomSyntacticSugar extends RandomSyntacticSugar
  private object FromSyntacticSugar extends FromSyntacticSugar
