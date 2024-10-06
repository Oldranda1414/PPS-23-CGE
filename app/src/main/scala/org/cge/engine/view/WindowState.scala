package org.cge.engine.view

import States.*
import Streams.*

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
   * Adds a button to the window.
   * 
   * @param text the text displayed on the button.
   * @param name a unique identifier for the button.
   * @return a state action that adds the button to the window.
   */
  def addButton(text: String, name: String): State[Window, Unit]

  /**
   * Adds a player to the game displayed in the window.
   * 
   * @param playerName the name of the player to be added.
   * @return a state action that adds the player to the window.
   */
  def addPlayer(playerName: String): State[Window, Unit]

  /**
   * Adds a card to a specific player in the window.
   * 
   * @param playerName the name of the player to receive the card.
   * @param cardValue the value of the card
   * @param cardSuit the suit of the card
   * @return a state action that adds the card to the player in the window.
   */
  def addCardToPlayer(playerName: String, cardValue: String, cardSuit: String): State[Window, Unit]

  /**
   * Displays the window on the screen.
   * 
   * @return a state action that makes the window visible.
   */
  def show(): State[Window, Unit]

  /**
   * Executes an arbitrary command within the context of the window.
   * 
   * @param cmd the command to be executed.
   * @return a state action that executes the command.
   */
  def exec(cmd: =>Unit): State[Window, Unit]

  /**
   * Retrieves a stream of events related to the window, such as user interactions.
   * 
   * @return a state action that produces a stream of events as strings.
   */
  def eventStream(): State[Window, Stream[String]]

object WindowStateImpl extends WindowState:
  import SwingFunctionalFacade.*
  
  type Window = Frame
  
  def initialWindow: Window = createFrame

  def setSize(width: Int, height: Int): State[Window, Unit] = 
    State(w => (w.setSize(width, height), {}))

  def addButton(text: String, name: String): State[Window, Unit] =
    State(w => (w.addButton(text, name), {}))
  
  def addPlayer(playerName: String): State[Window, Unit] =
    State(w => (w.addPlayer(playerName), {}))
  
  def addCardToPlayer(playerName: String, cardValue: String, cardSuit: String): State[Window, Unit] =
    State(w => (w.addCardToPlayer(playerName, cardValue, cardSuit), {}))

  def show(): State[Window, Unit] =
    State(w => (w.show, {}))
  
  def exec(cmd: =>Unit): State[Window, Unit] =
    State(w => (w, cmd))
  
  def eventStream(): State[Window, Stream[String]] =
    State(w => (w, Stream.generate(() => w.events().get)))

  def displayWinner(winner: String): State[Window, Unit] = 
    State(w => ((w.displayWinner(winner)), {}))

  def gameOver(): State[Window, Unit] = 
    State(w => ((w.gameOver()), {})) 