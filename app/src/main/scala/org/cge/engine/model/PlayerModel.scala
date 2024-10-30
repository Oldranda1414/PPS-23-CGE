package org.cge.engine.model

trait PlayerModel:
  def name: String
  def hand: DeckModel
  def points: Int
  def points_=(value: Int): Unit

object PlayerModel:
  def apply(name: String): PlayerModel = SimplePlayer(name)

  final case class SimplePlayer(val name: String) extends PlayerModel:
    val hand: DeckModel = DeckModel()

    private var playerPoints: Int = 0

    def points = playerPoints
    def points_=(value: Int): Unit = 
      require(value > 0, "Points must be greater than 0")
      playerPoints = value
