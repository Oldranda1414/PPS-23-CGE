package org.cge.engine.model

trait CardModel:
  def rank: Rank
  def suit: Suit

object CardModel:
  def apply(rank: Rank, suit: Suit): CardModel = SimpleCard(rank, suit)
  
  final case class SimpleCard(val rank: Rank, val suit: Suit) extends CardModel
