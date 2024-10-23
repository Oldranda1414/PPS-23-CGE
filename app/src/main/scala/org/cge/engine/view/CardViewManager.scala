package org.cge.engine.view

import WindowStateImpl.Window
import org.cge.engine.view.monads.States.State

object CardViewManager:

  private val cardWidth = 10
  private val cardHeight = 10

  def addCardToPlayer(windowState: State[Window, Unit], playerName: String, cardValue: String, cardSuit: String) =
    val cardText = cardValue + " of " + cardSuit
    val eventName = playerName + ":" + cardValue + " " + cardSuit

    ButtonViewManager.addButtonToPanel(windowState, playerName, eventName, cardText, cardWidth, cardHeight)
  