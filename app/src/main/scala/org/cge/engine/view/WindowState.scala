package org.cge.engine.view

import org.cge.engine.view.monads.States.State
import org.cge.engine.view.monads.Streams.Stream

import java.awt.Component
import javax.swing.JButton

/**
 * A trait that defines the state management interface for a window in the card game GUI.
 */
trait WindowState:

  /**
   * Type alias for the Window type.
   */
  type Window

  /**
   * Initializes and returns a new instance of the window.
   * 
   * @return the initial window instance.
   */
  def initialWindow: Window

  /**
   * Sets the size of the window.
   * 
   * @param width the width of the window in pixels.
   * @param height the height of the window in pixels.
   * @return a state action that updates the window's size.
   */
  def setSize(width: Int, height: Int): State[Window, Unit]

  /**
   * Adds a panel to the window.
   * 
   * @param panelName the name of the panel to be added.
   * @return a state action that adds the panel to the window.
   */
  def addPanel(panelName: String): State[Window, Unit]

  /**
   * Adds a component to the window's panel.
   * 
   * @param panelName the panel to which the component should be added.
   * @param component the component to add.
   * @return a state action that adds the component to the panel.
   */
  def addComponentToPanel(panelName: String, component: Component): State[Window, Unit]

  /**
   * Adds a component directly to the window.
   * 
   * @param name the unique identifier for the component.
   * @param component the component to add.
   * @return a state action that adds the component to the window.
   */
  def addComponent(name: String, component: Component): State[Window, Unit]

  /**
   * Sets the bounds of a component in the window.
   * 
   * @param name the unique identifier for the component.
   * @param x the x-coordinate of the component.
   * @param y the y-coordinate of the component.
   * @param width the width of the component.
   * @param height the height of the component.
   * @return a state action that sets the bounds for the component.
   */
  def setComponentBounds(name: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit]

  /**
   * Removes a component from the window.
   * 
   * @param name the unique identifier for the component to be removed.
   * @return a state action that removes the component.
   */
  def removeComponent(name: String): State[Window, Unit]

  /**
   * Displays the window on the screen.
   * 
   * @return a state action that makes the window visible.
   */
  def show(): State[Window, Unit]

  /**
   * Repaints the window.
   * 
   * @return a state action that repaints the window.
   */
  def repaint(): State[Window, Unit]

  /**
   * Retrieves a stream of events related to the window, such as user interactions.
   * 
   * @return a state action that produces a stream of events as strings.
   */
  def eventStream(): State[Window, Stream[String]]

  /**
   * Disposes of the window.
   * 
   * @return a state action that disposes of the window.
   */
  def dispose(): State[Window, Unit]

/**
 * Concrete implementation of the WindowState trait.
 */
object WindowStateImpl extends WindowState:
  import SwingFunctionalFacade.*

  type Window = Frame

  def initialWindow: Window = createFrame()

  def setSize(width: Int, height: Int): State[Window, Unit] = 
    State { w => (w.setSize(width, height), {}) }

  def addPanel(panelName: String): State[Window, Unit] =
    State { w => (w.addPanel(panelName), {}) }
  
  def addBoxLayout(panelName: String): State[Window, Unit] =
    State { w => (w.addBoxLayout(panelName), {}) }

  def addPanelTitle(panelName: String, title: String): State[Window, Unit] =
    State { w => (w.addPanelTitle(panelName, title), {}) }

  def addComponentToPanel(panelName: String, component: Component): State[Window, Unit] =
    State { w => (w.addComponentToPanel(panelName, component), {}) }

  def addComponent(name: String, component: Component): State[Window, Unit] =
    State { w => (w.addComponent(name, component), {}) }

  def setComponentBounds(name: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit] =
    State { w => (w.setComponentBounds(name, x, y, width, height), {}) }

  def removeComponent(name: String): State[Window, Unit] =
    State { w => (w.removeComponent(name), {}) }

  def addButton(jb: JButton, eventName: String): State[Window, Unit] =
    State { w => (w.addButton(jb, eventName), {}) }

  def show(): State[Window, Unit] =
    State { w => (w.show(), {}) }

  def repaint(): State[Window, Unit] =
    State { w => (w.repaint(), {}) }

  def eventStream(): State[Window, Stream[String]] =
    State { w => (w, Stream.generate(() => w.events().get)) }

  def dispose(): State[Window, Unit] =
    State { w => (w.dispose(), {}) }
  
  def exec(cmd: =>Unit): State[Window, Unit] =
    State(w => (w, cmd))

