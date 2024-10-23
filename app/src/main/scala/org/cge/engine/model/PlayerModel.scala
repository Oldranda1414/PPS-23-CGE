package org.cge.engine.model

trait PlayerModel:
  def name: String
  def hand: DeckModel

object PlayerModel:
  def apply(name: String): PlayerModel = SimplePlayer(name)

  final case class SimplePlayer(val name: String) extends PlayerModel:
    val hand: DeckModel = DeckModel()
