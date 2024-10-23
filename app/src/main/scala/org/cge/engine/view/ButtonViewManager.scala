package org.cge.engine.view

import WindowStateImpl.Window
import org.cge.engine.view.monads.States.State

import javax.swing.JButton

object ButtonViewManager:

  def addButton(windowState: State[Window, Unit], eventName: String, buttonText: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit] =
    for
      _ <- windowState
      _ <- WindowStateImpl.addButton(createButton(eventName, buttonText, x, y, width, height), eventName)
    yield ()

  def addButtonToPanel(windowState: State[Window, Unit], panelName: String, eventName: String, buttonText: String, width: Int, height: Int): State[Window, Unit] =
    val button: JButton = createButton(eventName, buttonText, 0, 0, width, height)

    for
      _ <- windowState
      _ <- WindowStateImpl.addButton(button, eventName)
      _ <- WindowStateImpl.addComponentToPanel(panelName, button)
    yield ()
  
  def createButton(name: String, text: String, x: Int, y: Int, width: Int, height: Int): JButton =
    val jb: JButton = new JButton(text);
    jb.setBounds(x, y, width, height);
    jb.setActionCommand(name);
    jb

