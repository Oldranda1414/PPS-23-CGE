package org.cge.engine.model

/** An opaque type representing a suit. */
opaque type Suit = String

/** A companion object for the Suit opaque type. */
object Suit:
  /** A factory method to create a Suit from a String. */
  def apply(suit: String): Suit = suit

  /** Standard suits in a french deck */
  val standardSuits: Set[Suit] = Set(
    "Clubs",
    "Diamonds",
    "Hearts",
    "Spades"
  )

  /** A given instance of Conversion[String, Suit] to convert a String to a Suit. */
  given Conversion[String, Suit] with
    def apply(suit: String): Suit = Suit(suit)
