package org.cge.engine

trait Card:
  def value: String
  def suit: String

final case class SimpleCard(val value: String, val suit: String) extends Card
