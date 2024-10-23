package org.cge.engine.view

import WindowStateImpl.Window
import org.cge.engine.view.monads.States.State

import javax.swing.JPanel
import javax.swing.JLabel
import javax.swing.BorderFactory
import java.awt.Color
import java.awt.Font

object CardViewManager:

  private val cardWidth = 10
  private val cardHeight = 10
  private val cardFont: Font = new Font("Arial", Font.PLAIN, Math.min(cardWidth, cardHeight));

  def addCardToPlayer(windowState: State[Window, Unit], playerName: String, cardValue: String, cardSuit: String) =
    val card: JPanel = createCardView(cardValue, cardSuit)
    card.setBounds(0, 0, cardWidth, cardHeight)

    for
      _ <- windowState
      _ <- WindowStateImpl.addComponentToPanel(playerName, card)
    yield ()
  
  private def createCardView(cardValue: String, cardSuit: String) = 
    val cardPanel: JPanel = new JPanel();
    cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    val cardLabel: JLabel = new JLabel(cardValue + " of " + cardSuit);
    cardLabel.setFont(cardFont)

    cardPanel.add(cardLabel);

    cardPanel
  