package org.cge.engine.view

import WindowStateImpl.Window
import org.cge.engine.view.monads.States.State

sealed trait PlayerPosition
case object Up extends PlayerPosition
case object Down extends PlayerPosition
case object Left extends PlayerPosition
case object Right extends PlayerPosition

object PlayerViewManager:
  import org.cge.engine.WindowDimentions.*

  private val upPlayerDims = (700, 100)
  private val rightPlayerDims = (100, 500)
  private val downPlayerDims = (500, 100)
  private val leftPlayerDims = (100, 500)

  private val upPlayerCoords = (20, 20)
  private val rightPlayerCoords = (windowWidth - 200, 20)
  private val downPlayerCoords = (windowWidth - 200 - downPlayerDims._1, windowHeight - 200)
  private val leftPlayerCoords = (20, windowHeight - 200)

  private val possiblePositions = Seq(Up, Down, Right, Left)
  private var playerPositions: Map[String, PlayerPosition] = Map.empty[String, PlayerPosition]

  def addPlayer(windowState: State[Window, Unit], playerName: String): State[Window, Unit] =
    if (playerPositions.size >= possiblePositions.length)
      throw new IllegalArgumentException("Cannot add player " + playerName + ": A maximum of 4 players is allowed")
    else
      playerPositions += (playerName -> possiblePositions(playerPositions.size))
    
    val (playerX, playerY): (Int, Int) = getPlayerHandCoords(playerName)
    val (playerWidth, playerHeight) = getPlayerDims(playerName)
    val playerOrientation: Boolean = getPlayerOrientation(playerName)

    for
      _ <- windowState
      _ <- WindowStateImpl.addPanel(playerName, playerX, playerY, playerWidth, playerHeight)
      _ <- WindowStateImpl.addGridLayout(playerName, playerOrientation)
      _ <- WindowStateImpl.addPanelTitle(playerName, playerName)
    yield ()
  
  private def getPlayerHandCoords(playerName: String): (Int, Int) = 
    getPlayerPosition(playerName) match
      case Up => upPlayerCoords
      case Down => downPlayerCoords
      case Left => leftPlayerCoords
      case Right => rightPlayerCoords

  private def getPlayerOrientation(playerName: String): Boolean = 
    getPlayerPosition(playerName) match
      case Up => false
      case Down => false
      case Left => true
      case Right => true

  private def getPlayerDims(playerName: String): (Int, Int) = 
    getPlayerPosition(playerName) match
      case Up => upPlayerDims
      case Down => downPlayerDims
      case Left => leftPlayerDims
      case Right => rightPlayerDims
  
  private def getPlayerPosition(playerName: String): PlayerPosition =
    playerPositions.get(playerName) match
      case Some(position) => position
      case None => throw new NoSuchElementException(s"Player $playerName position not found")
    
  
