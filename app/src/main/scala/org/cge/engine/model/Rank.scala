package org.cge.engine.model

/** An opaque type representing a rank. */
opaque type Rank = String

/** A companion object for the Rank opaque type. */
object Rank:
  /** A factory method to create a Rank from a String. */
  def apply(rank: String): Rank = rank

  /** Standard ranks in a french deck */
  val standardRanks: List[Rank] = List(
    "2",
    "3",
    "4",
    "5",
    "6",
    "7",
    "8",
    "9",
    "10",
    "Jack",
    "Queen",
    "King",
    "Ace"
  )

  /** A given instance of Conversion[String, Rank] to convert a String to a Rank. */
  given Conversion[String, Rank] with
    def apply(rank: String): Rank = Rank(rank)
