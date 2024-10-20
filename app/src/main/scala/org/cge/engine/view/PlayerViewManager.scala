package org.cge.engine.view

import WindowStateImpl.Window
import org.cge.engine.view.monads.States.State

import javax.swing.JPanel
import javax.swing.JLabel
import javax.swing.BorderFactory
import java.awt.Color

object PlayerViewManager:

  def addPlayer(windowState: State[Window, Unit], playerName: String): State[Window, Unit] =
    for
      _ <- windowState
      _ <- WindowStateImpl.addPanel(playerName)
      _ <- WindowStateImpl.addBoxLayout(playerName)
      _ <- WindowStateImpl.addPanelTitle(playerName, playerName)
    yield ()
  
  def addCardToPlayer(windowState: State[Window, Unit], playerName: String, cardValue: String, cardSuit: String) =
    for
      _ <- windowState
      _ <- WindowStateImpl.addComponentToPanel(playerName, createCardView(cardValue, cardSuit))
    yield ()
  
  def createCardView(cardValue: String, cardSuit: String) = 

    val cardPanel: JPanel = new JPanel();
    cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    val cardLabel: JLabel = new JLabel(cardValue + " of " + cardSuit);
    cardPanel.add(cardLabel);

