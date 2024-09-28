package org.cge.engine

object Game:
  trait Card:
    def value: String
    def suit: String

  trait Player:
    def name: String

  final case class SimpleCard(val value: String, val suit: String) extends Card

  final case class SimplePlayer(val name: String) extends Player
