package org.cge.engine.view

import org.cge.engine.view.monads.States.State
import org.cge.engine.view.monads.Streams.Stream

import java.awt.Component
import javax.swing.JButton

/**
  * An object that maps the methods of the java GUI facade to return state pattern.
  */
object WindowState:
  import SwingFunctionalFacade.*

  type Window = Frame

  def initialWindow: Window = createFrame()

  /**
    * Sets the size of the window of the GUI.
    *
    * @param width the width of the window.
    * @param height the height of the window.
    * @return the updated GUI state.
    */
  def setSize(width: Int, height: Int): State[Window, Unit] = 
    State { w => (w.setSize(width, height), {}) }

  /**
    * Adds a panel to the GUI.
    *
    * @param panelName the name of the panel.
    * @param x the x coordinate of the panel.
    * @param y the y coordinate of the panel.
    * @param width the width of the panel.
    * @param height the height of the panel.
    * @return the updated GUI state.
    */
  def addPanel(panelName: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit] =
    State { w => (w.addPanel(panelName, x, y, width, height), {}) }
  
  /**
    * Adds a grid layout to a panel in the GUI.
    *
    * @param panelName the name of the panel the layout should be added to..
    * @param orientation the orientation of the layout.
    * @return the updated GUI state.
    */
  def addGridLayout(panelName: String, orientation: Boolean): State[Window, Unit] =
    State { w => (w.addGridLayout(panelName, orientation), {}) }

  /**
    * Sets a title of a given panel.
    *
    * @param panelName the name of the panel to add the title to.
    * @param title the panel title.
    * @return the updated GUI state.
    */
  def addPanelTitle(panelName: String, title: String): State[Window, Unit] =
    State { w => (w.addPanelTitle(panelName, title), {}) }

  /**
    * Adds a component to a panel.
    *
    * @param panelName the name of the panel the component should be added to.
    * @param component the component to be added.
    * @return the updated GUI state.
    */
  def addComponentToPanel(panelName: String, component: Component): State[Window, Unit] =
    State { w => (w.addComponentToPanel(panelName, component), {}) }

  /**
    * remove a component from a panel.
    *
    * @param panelName the name of the panel the component should be removed from.
    * @param component the component to be removed.
    * @return the updated GUI state.
    */
  def removeComponentFromPanel(panelName: String, component: Component): State[Window, Unit] =
    State { w => (w.removeComponentFromPanel(panelName, component), {}) }

  /**
    * Adds a component to the GUI
    *
    * @param name the name of the component to be added.
    * @param component the component to be added.
    * @return the updated GUI state.
    */
  def addComponent(name: String, component: Component): State[Window, Unit] =
    State { w => (w.addComponent(name, component), {}) }

  /**
    * Sets the bounds of a component.
    *
    * @param name the name of the componente.
    * @param x the x coordinate of the component.
    * @param y the y coordinate of the component.
    * @param width the width of the component.
    * @param height the width of the component.
    * @return the updated GUI state.
    */
  def setComponentBounds(name: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit] =
    State { w => (w.setComponentBounds(name, x, y, width, height), {}) }

  /**
    * Removes a component from the GUI.
    *
    * @param name the name of the component to be removed.
    * @return the updated GUI state.
    */
  def removeComponent(name: String): State[Window, Unit] =
    State { w => (w.removeComponent(name), {}) }

  /**
    * Adds a button to the GUI.
    *
    * @param jb the button to be added.
    * @param eventName the event the button should publish on click.
    * @return the updated GUI state.
    */
  def addButton(jb: JButton, eventName: String): State[Window, Unit] =
    State { w => (w.addButton(jb, eventName), {}) }

  /**
    * Removes button from the GUI.
    *
    * @param jb the button to be removed.
    * @return the updated GUI state.
    */
  def removeButton(jb: JButton): State[Window, Unit] =
    State { w => (w.removeButton(jb), {}) }

  /**
    * Adds a label to the GUI.
    *
    * @param text the text of the label.
    * @param x the x coordinate of the label.
    * @param y the y coordinate of the label.
    * @param width the width of the label.
    * @param height the height of the label.
    * @return the updated GUI state.
    */
  def addLabel(text: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit] =
    State { w => (w.addLabel(text, x, y, width, height), {}) }

  /**
    * Makes the GUI visible.
    * 
    * @return the updated GUI state.
    */
  def show(): State[Window, Unit] =
    State { w => (w.show(), {}) }

  /**
    * @return a state that returns the stream of events published by the GUI.
    */
  def eventStream(): State[Window, Stream[String]] =
    State { w => (w, Stream.generate(() => w.events().get)) }
  
  /**
    * Executes a given command.
    *
    * @param cmd the command to be executed.
    */
  def exec(cmd: =>Unit): State[Window, Unit] =
    State(w => (w, cmd))

