# Implementation

- [Biagini Andrea](#biagini-andrea)
- [Gurioli Filippo](#gurioli-filippo)
- [Randacio Leonardo](#randacio-leonardo)
- [Pair Programming](#pair-programming)
  - [GameController](#gamecontroller)

In the following every member of the team details the parts of the project that they have developed

## [Biagini Andrea](biagini.md)

## [Gurioli Filippo](gurioli.md)

## [Randacio Leonardo](randacio.md)

## Pair Programming

The following parts have been developed in pair programming.

### GameController

The `GameController` has been developed in Pair Programming by Filipo Gurioli and Leonardo Randacio.

The Controller takes a Model and uses it to build the initial View State. It defines the methods to be run on every event published by the View and then runs the initial View State.

The Controller parses the events published by the View playing a given card if a player is trying to play it.

```scala
  val windowEventsHandling: State[Window, Unit] = for
    events <- windowCreation
    _ <- seqN(events.map(e =>
      val parsedEvent = e.split(":")
      val eventName = parsedEvent(0)
      eventName match
        // Checking if eventName is the name of one of the players.
        case s if game.players.map(_.name).contains(eventName) =>
          handleCardPlayed(s, parsedEvent(1))
        case _ =>
          // If the eventName does not correspond to a player name
          // a state that does not update the GUI is returned.
          unitState()
    ))
  yield ()
```

The `handleCardPlayed` function checks if the player that played the card is the turn player and can play that card at that moment.

```scala
  private def handleCardPlayed(playerName: String, cardFromEvent: String): State[Window, Unit] =
    val rank = cardFromEvent.split(" ")(0)
    val suit = cardFromEvent.split(" ")(1)
    val card = CardModel(rank, suit)

    game.players.find(_.name == playerName) match
      case Some(player) =>
        if game.turn.name != playerName || !game.canPlayCard(game.turn, card) then
          unitState()
        else playCard(player, card)
      case None =>
        throw new NoSuchElementException(s"Player $playerName not found")
```

The `playCard` function updates the Model and returns the correct state update, depending on if the hand has finished or not.

```scala
  private def playCard(player: PlayerModel, card: CardModel) =
    turnCounter += 1
    game.playCard(player, card)
    if turnCounter == game.players.size then
      turnCounter = 0
      moveCardToTable(player, card).flatMap(_ =>
        endHand())
    else moveCardToTable(player, card)
```

[Back to index](../index.md) |
[Previous Chapter](../5_detailed_design/index.md) |
[Next Chapter](../7_testing/index.md)
