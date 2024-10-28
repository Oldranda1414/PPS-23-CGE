package org.cge.engine.view

import WindowState.Window
import org.cge.engine.view.monads.States.State

sealed trait PlayerPosition
case object Center extends PlayerPosition
case object Up extends PlayerPosition
case object Down extends PlayerPosition
case object Left extends PlayerPosition
case object Right extends PlayerPosition

object PlayerViewManager:
  import org.cge.engine.WindowDimentions.*

  private val horizontalPlayerDims = (500, 100)
  private val verticalPlayerDims = (100, 500)

  private val centerPlayerDims = horizontalPlayerDims
  private val upPlayerDims = horizontalPlayerDims
  private val rightPlayerDims = verticalPlayerDims
  private val downPlayerDims = horizontalPlayerDims
  private val leftPlayerDims = verticalPlayerDims

  private val centerPlayerCoords = (200, windowHeight/2)
  private val upPlayerCoords = (20, 20)
  private val rightPlayerCoords = (windowWidth - 200, 20)
  private val downPlayerCoords = (windowWidth - 200 - downPlayerDims._1, windowHeight - 200)
  private val leftPlayerCoords = (20, /*40 + upPlayerDims._2*/ windowHeight - leftPlayerDims._2 - 80)

  private val possiblePositions = Seq(Center, Up, Down, Right, Left)
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
      _ <- WindowState.addPanel(playerName, playerX, playerY, playerWidth, playerHeight)
      _ <- WindowState.addGridLayout(playerName, playerOrientation)
      _ <- WindowState.addPanelTitle(playerName, playerName)
    yield ()
  
  private def getPlayerHandCoords(playerName: String): (Int, Int) = 
    getPlayerPosition(playerName) match
      case Center => centerPlayerCoords
      case Up => upPlayerCoords
      case Down => downPlayerCoords
      case Left => leftPlayerCoords
      case Right => rightPlayerCoords

  private def getPlayerOrientation(playerName: String): Boolean = 
    getPlayerPosition(playerName) match
      case Center => false
      case Up => false
      case Down => false
      case Left => true
      case Right => true

  private def getPlayerDims(playerName: String): (Int, Int) = 
    getPlayerPosition(playerName) match
      case Center => centerPlayerDims
      case Up => upPlayerDims
      case Down => downPlayerDims
      case Left => leftPlayerDims
      case Right => rightPlayerDims
  
  private def getPlayerPosition(playerName: String): PlayerPosition =
    playerPositions.get(playerName) match
      case Some(position) => position
      case None => throw new NoSuchElementException(s"Player $playerName position not found")
    
  
