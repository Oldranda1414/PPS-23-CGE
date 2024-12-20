package org.cge.engine.view

import WindowState.Window
import org.cge.engine.view.monads.States.State

import javax.swing.JButton

/**
  * Defines methods to manage buttons in the GUI.
  */
object ButtonViewManager:
  var panelButtons: Map[String, List[JButton]] = Map.empty

  /**
    * Adds a button to the GUI.
    *
    * @param eventName the event that should be published on the button being pressed.
    * @param buttonText the button text.
    * @param x the buttons x position.
    * @param y the buttons y position.
    * @param width the width of the button.
    * @param height the height of the button.
    * @return the updated window state.
    */
  def addButton(eventName: String, buttonText: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit] =
    for
      _ <- WindowState.addButton(createButton(eventName, buttonText, x, y, width, height), eventName)
    yield ()

  /**
    * Adds a button to a panel in the GUI.
    *
    * @param panelName the panel the button should be added to.
    * @param eventName the event that should be published on the button being pressed.
    * @param buttonText the button text.
    * @param width the width of the button.
    * @param height the height of the button.
    * @return the updated window state.
    */
  def addButtonToPanel(panelName: String, eventName: String, buttonText: String, width: Int, height: Int): State[Window, Unit] =
    val button: JButton = createButton(eventName, buttonText, 0, 0, width, height)

    panelButtons = panelButtons.updatedWith(panelName):
      case Some(buttons) => Some(button :: buttons)
      case None => Some(List(button))

    for
      _ <- WindowState.addButton(button, eventName)
      _ <- WindowState.addComponentToPanel(panelName, button)
    yield ()
  
  /**
    * Removes a button from a panel in the GUI.
    *
    * @param panelName the panel the button should be removed from.
    * @param buttonText the text of the button to be removed.
    * @return the updated window state.
    */
  def removeButtonFromPanel(panelName: String, buttonText: String): State[Window, Unit] =
    val button: JButton = popButtonFromPanelButtons(panelName, buttonText)

    for
      _ <- WindowState.removeComponentFromPanel(panelName, button)
      _ <- WindowState.removeButton(button)
    yield ()

  /**
  * Removes all buttons from a panel in the GUI.
  *
  * @param panelName the panel to be cleared.
  * @return the updated window state.
  */
  def clearPanel(panelName: String): State[Window, Unit] =
    panelButtons.get(panelName) match
      case Some(buttons) =>
        panelButtons = panelButtons - panelName

        buttons.foldLeft(unitState()): (acc, button) =>
          for
            _ <- acc
            _ <- WindowState.removeComponentFromPanel(panelName, button)
            _ <- WindowState.removeButton(button)
          yield ()

      case None => unitState()
  
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

  private def unitState(): State[Window, Unit] = State(s => (s, ()))


