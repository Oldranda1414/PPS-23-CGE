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

  private val horizontalPlayerDims = (windowWidth / 2, windowHeight / 10)
  private val verticalPlayerDims = (windowWidth / 10, windowHeight / 2)

  private val centerPlayerDims = horizontalPlayerDims
  private val upPlayerDims = horizontalPlayerDims
  private val rightPlayerDims = verticalPlayerDims
  private val downPlayerDims = horizontalPlayerDims
  private val leftPlayerDims = verticalPlayerDims

  private val widthPadding = windowWidth / 12
  private val heightPadding = windowHeight / 12

  private val centerPlayerCoords = (windowWidth / 5, windowHeight / 2)
  private val upPlayerCoords = (widthPadding, heightPadding)
  private val rightPlayerCoords = (windowWidth - (rightPlayerDims._1 + widthPadding), heightPadding)
  private val downPlayerCoords = (windowWidth - (downPlayerDims._1 + widthPadding), windowHeight - (downPlayerDims._2 + heightPadding))
  private val leftPlayerCoords = (widthPadding, windowHeight - (leftPlayerDims._2 + heightPadding))

  private val possiblePositions = Seq(Center, Up, Down, Right, Left)
  private var playerPositions: Map[String, PlayerPosition] = Map.empty[String, PlayerPosition]

  def addPlayer(windowState: State[Window, Unit], playerName: String): State[Window, Unit] =
    if (playerPositions.size >= possiblePositions.length)
      throw new IllegalArgumentException(s"Cannot add player $playerName: A maximum of 4 players is allowed")
    
    playerPositions += (playerName -> possiblePositions(playerPositions.size))

    val (playerX, playerY) = getPlayerHandCoords(playerName)
    val (playerWidth, playerHeight) = getPlayerDims(playerName)
    val playerOrientation = getPlayerOrientation(playerName)

    for
      _ <- windowState
      _ <- WindowState.addPanel(playerName, playerX, playerY, playerWidth, playerHeight)
      _ <- WindowState.addGridLayout(playerName, playerOrientation)
      _ <- WindowState.addPanelTitle(playerName, playerName)
    yield ()
    
  private def getPlayerPosition(playerName: String): PlayerPosition = 
    playerPositions.get(playerName) match
      case Some(position) => position
      case None => throw new NoSuchElementException(s"Player $playerName position not found")

  private def getPlayerHandCoords(playerName: String): (Int, Int) =
    getPlayerPosition(playerName) match
      case Center => centerPlayerCoords
      case Up => upPlayerCoords
      case Down => downPlayerCoords
      case Left => leftPlayerCoords
      case Right => rightPlayerCoords

  private def getPlayerOrientation(playerName: String): Boolean =
    getPlayerPosition(playerName) match
      case Left | Right => true
      case Center | Up | Down => false

  private def getPlayerDims(playerName: String): (Int, Int) =
    getPlayerPosition(playerName) match
      case Center => centerPlayerDims
      case Up => upPlayerDims
      case Down => downPlayerDims
      case Left => leftPlayerDims
      case Right => rightPlayerDims
