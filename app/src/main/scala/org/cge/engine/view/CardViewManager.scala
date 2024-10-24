package org.cge.engine.view

import WindowState.Window
import org.cge.engine.view.monads.States.State

object CardViewManager:

  private val cardWidth = 10
  private val cardHeight = 10

  def addCardToPlayer(windowState: State[Window, Unit], playerName: String, cardValue: String, cardSuit: String) =
    val (cardEvent, cardText) = cardEventAndText(playerName, cardValue, cardSuit)
    ButtonViewManager.addButtonToPanel(windowState, playerName, cardEvent, cardText, cardWidth, cardHeight)

  def removeCardFromPlayer(windowState: State[Window, Unit], playerName: String, cardValue: String, cardSuit: String) =
    ButtonViewManager.removeButtonFromPanel(windowState, playerName, cardText(cardValue, cardSuit))
  
  def cardEventAndText(playerName: String, cardValue: String, cardSuit: String): (String, String) =
    (cardEvent(playerName, cardValue, cardSuit), cardText(cardValue, cardSuit))
  
  def cardEvent(playerName: String, cardValue: String, cardSuit: String) =
    s"$playerName:$cardValue $cardSuit"
  
  def cardText(cardValue: String, cardSuit: String): String =
    s"<html>$cardValue<br>of<br>$cardSuit</html>"
