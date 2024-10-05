package org.cge.engine.view

import Monads.*, Monad.*, States.*, State.*
import Streams.*

trait WindowState:
  type Window
  def initialWindow: Window
  def setSize(width: Int, height: Int): State[Window, Unit]
  def addPlayer(playerName: String): State[Window, Unit]
  def addCardToPlayer(playerName: String, cardValue: String, cardSuit: String): State[Window, Unit]
  def show(): State[Window, Unit]
  def exec(cmd: =>Unit): State[Window, Unit]
  def eventStream(): State[Window, Stream[String]]

object WindowStateImpl extends WindowState:
  import SwingFunctionalFacade.*
  
  type Window = Frame
  
  def initialWindow: Window = createFrame

  def setSize(width: Int, height: Int): State[Window, Unit] = 
    State(w => (w.setSize(width, height), {}))
  
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