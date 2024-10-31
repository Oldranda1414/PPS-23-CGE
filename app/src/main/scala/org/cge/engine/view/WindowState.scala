package org.cge.engine.view

import org.cge.engine.view.monads.States.State
import org.cge.engine.view.monads.Streams.Stream

import java.awt.Component
import javax.swing.JButton

object WindowState:
  import SwingFunctionalFacade.*

  type Window = Frame

  def initialWindow: Window = createFrame()

  def setSize(width: Int, height: Int): State[Window, Unit] = 
    State { w => (w.setSize(width, height), {}) }

  def addPanel(panelName: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit] =
    State { w => (w.addPanel(panelName, x, y, width, height), {}) }
  
  def addGridLayout(panelName: String, orientation: Boolean): State[Window, Unit] =
    State { w => (w.addGridLayout(panelName, orientation), {}) }

  def addPanelTitle(panelName: String, title: String): State[Window, Unit] =
    State { w => (w.addPanelTitle(panelName, title), {}) }

  def addComponentToPanel(panelName: String, component: Component): State[Window, Unit] =
    State { w => (w.addComponentToPanel(panelName, component), {}) }

  def removeComponentFromPanel(panelName: String, component: Component): State[Window, Unit] =
    State { w => (w.removeComponentFromPanel(panelName, component), {}) }

  def addComponent(name: String, component: Component): State[Window, Unit] =
    State { w => (w.addComponent(name, component), {}) }

  def setComponentBounds(name: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit] =
    State { w => (w.setComponentBounds(name, x, y, width, height), {}) }

  def removeComponent(name: String): State[Window, Unit] =
    State { w => (w.removeComponent(name), {}) }

  def addButton(jb: JButton, eventName: String): State[Window, Unit] =
    State { w => (w.addButton(jb, eventName), {}) }

  def removeButton(jb: JButton): State[Window, Unit] =
    State { w => (w.removeButton(jb), {}) }

  def addLabel(text: String, x: Int, y: Int, width: Int, height: Int): State[Window, Unit] =
    State { w => (w.addLabel(text, x, y, width, height), {}) }

  def show(): State[Window, Unit] =
    State { w => (w.show(), {}) }

  def eventStream(): State[Window, Stream[String]] =
    State { w => (w, Stream.generate(() => w.events().get)) }
  
  def exec(cmd: =>Unit): State[Window, Unit] =
    State(w => (w, cmd))

