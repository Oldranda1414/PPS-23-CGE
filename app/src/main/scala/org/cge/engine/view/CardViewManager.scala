package org.cge.engine.view

import WindowState.Window
import org.cge.engine.view.monads.States.State

/**
  * Defines methods to cards in the GUI.
  */
object CardViewManager:

  private val cardWidth = 10
  private val cardHeight = 10

  /**
    * Adds a card to a player in the GUI.
    *
    * @param playerName the player to add the card to.
    * @param cardValue the value of the card to be added.
    * @param cardSuit the suit of the card to be added.
    * @return the updated window state.
    */
  def addCardToPlayer(playerName: String, cardValue: String, cardSuit: String): State[Window, Unit] =
    val (cardEvent, cardText) = cardEventAndText(playerName, cardValue, cardSuit)
    ButtonViewManager.addButtonToPanel(playerName, cardEvent, cardText, cardWidth, cardHeight)

  /**
    * Removes a card to a player in the GUI.
    *
    * @param playerName the player to remove the card from.
    * @param cardValue the value of the card to be removed.
    * @param cardSuit the suit of the card to be removed.
    * @return the updated window state.
    */
  def removeCardFromPlayer(playerName: String, cardValue: String, cardSuit: String): State[Window, Unit] =
    ButtonViewManager.removeButtonFromPanel(playerName, cardText(cardValue, cardSuit))

  /**
    * Removes all card from a player hand in the GUI
    *
    * @param playerName the player whos hand should be cleared.
    * @return the updated window state.
    */
  def clearPlayerHand(playerName: String): State[Window, Unit] =
    ButtonViewManager.clearPanel(playerName)
  
  private def cardEventAndText(playerName: String, cardValue: String, cardSuit: String): (String, String) =
    (cardEvent(playerName, cardValue, cardSuit), cardText(cardValue, cardSuit))
  
  private def cardEvent(playerName: String, cardValue: String, cardSuit: String): String =
    s"$playerName:$cardValue $cardSuit"
  
  private def cardText(cardValue: String, cardSuit: String): String =
    s"<html>$cardValue<br>of<br>$cardSuit</html>"
