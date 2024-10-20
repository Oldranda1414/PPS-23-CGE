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
  
  def createButton(name: String, text: String, x: Int, y: Int, width: Int, height: Int): JButton =
    val jb: JButton = new JButton(text);
    jb.setBounds(x, y, width, height);
    jb.setActionCommand(name);
    jb

