# Goon President Rules

**Goon President** is a card game for 4 to 6 players, played with a 40-card deck of [Italian playing cards](https://en.wikipedia.org/wiki/Italian_playingcards).

## Objective

The goal is to be the first player to play all the cards in your hand.

## Setup

1. Shuffle the deck and deal the cards evenly to all players.
2. Choose a player to start the first hand.

## How to Play

The game is played in a series of hands. Each hand proceeds as follows:

1. **Starting the Hand:**  
   - The first player (or the winner of the previous hand) plays a set of cards with the same value (e.g., one card with a value of 5, or two cards with a value of 3).
  
2. **Player Turns:**  
   - The next player must either:
     - Play the same number of cards with a higher value, or
     - Pass their turn.
   - Players continue in this way around the table.

3. **Card Strength (Highest to Lowest):**  
   - 2  
   - King  
   - Knight  
   - Knave  
   - 7  
   - 6  
   - 5, and so on, down to 3.

4. **Ending a Hand:**  
   - A hand ends when either all players pass, leaving one player with the highest card(s), or when a 2 is played.
   - The winner of the hand starts the next hand.
   - Discard the played cards for the rest of the game.

```scala
game playing rules are (
   players can play ranks higherThan the previousPlayer,
   players can pass their turn,
   hands end when a 2 is played
)
```
