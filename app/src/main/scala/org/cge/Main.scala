package org.cge

import engine.Game._
import engine.view.WindowStateImpl
import engine.view.Monads.Monad.seqN

object Main:
  def main(args: Array[String]): Unit =
    val card1 = SimpleCard("1", "spades")
    val card2 = SimpleCard("2", "spades")
    val card3 = SimpleCard("3", "spades")

  def greeting(message: String = "Hello, world!"): String = message

  // testing functional gui
  val windowCreation = for 
    _ <- WindowStateImpl.setSize(300, 300)
    _ <- WindowStateImpl.addButton(text = "inc", name = "IncButton")
    _ <- WindowStateImpl.addButton(text = "dec", name = "DecButton")
    _ <- WindowStateImpl.addButton(text = "quit", name = "QuitButton")
    _ <- WindowStateImpl.addLabel(text = "-", name = "Label1")
    _ <- WindowStateImpl.show()
    e <- WindowStateImpl.eventStream()
  yield e

  val windowEventsHandling = for
    e <- windowCreation
    // e <- eventStream()
    _ <- seqN(e.map(_ match
        case "IncButton" => WindowStateImpl.toLabel("i", "Label1")
        case "DecButton" => WindowStateImpl.toLabel("d", "Label1")
        case "QuitButton" => WindowStateImpl.exec(sys.exit())))
  yield ()

  windowEventsHandling.run(WindowStateImpl.initialWindow)