package org.cge.engine.view

import WindowState.Window
import org.cge.engine.view.monads.States.State

import javax.swing.JButton

object ButtonViewManager:
  var panelButtons: Map[String, List[JButton]] = Map.empty

  def addButton(windowState: State[Window, Unit], eventName: String, buttonText: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit] =
    for
      _ <- windowState
      _ <- WindowState.addButton(createButton(eventName, buttonText, x, y, width, height), eventName)
    yield ()

  def addButtonToPanel(windowState: State[Window, Unit], panelName: String, eventName: String, buttonText: String, width: Int, height: Int): State[Window, Unit] =
    val button: JButton = createButton(eventName, buttonText, 0, 0, width, height)

    panelButtons = panelButtons.updatedWith(panelName):
      case Some(buttons) => Some(button :: buttons)
      case None => Some(List(button))

    if panelName == "table" then
      println(panelButtons(panelName).size)

    for
      _ <- windowState
      _ <- WindowState.addButton(button, eventName)
      _ <- WindowState.addComponentToPanel(panelName, button)
    yield ()
  
  def removeButtonFromPanel(windowState: State[Window, Unit], panelName: String, buttonText: String): State[Window, Unit] =
    val button: JButton = popButtonFromPanelButtons(panelName, buttonText)

    for
      _ <- windowState
      _ <- WindowState.removeComponentFromPanel(panelName, button)
      _ <- WindowState.removeButton(button)
    yield ()

  def clearPanel(windowState: State[Window, Unit], panelName: String): State[Window, Unit] =
    var newWindowState: State[Window, Unit] = windowState
    panelButtons.get(panelName) match
      case Some(buttons) =>
        // val button = buttons.head
        // for 
        //   _ <- windowState
        //   _ <- WindowState.removeComponentFromPanel(panelName, button)
        //   _ <- WindowState.removeButton(button)
        // yield()
        panelButtons = panelButtons - panelName
        buttons.foreach(button =>
          println(button.getText())
          newWindowState = for
            _ <- newWindowState
            _ <- WindowState.removeComponentFromPanel(panelName, button)
            _ <- WindowState.removeButton(button)
          yield ()
        )
      case None => throw new Exception("something is wrong")

    newWindowState
  
  private def createButton(name: String, text: String, x: Int, y: Int, width: Int, height: Int): JButton =
    val jb: JButton = new JButton(text);
    jb.setBounds(x, y, width, height);
    jb.setActionCommand(name);
    jb
  
  private def popButtonFromPanelButtons(panelName: String, buttonText: String): JButton =
    panelButtons.get(panelName) match
      case Some(buttons) =>
        buttons.find(_.getText == buttonText) match
          case Some(button) =>
            val updatedButtons = buttons.filterNot(_.getText == buttonText)
            panelButtons = if updatedButtons.isEmpty then panelButtons - panelName else panelButtons.updated(panelName, updatedButtons)
            button
          case None =>
            throw new NoSuchElementException(s"Button with text '$buttonText' not found for panel '$panelName'.")
      case None =>
          throw new NoSuchElementException(s"Panel '$panelName' has no buttons")


