# Andrea Biagini

In the project implementation, I contributed working on `GameModel`, `PlayerModel`, `CardModel`, `DeckModel` and `TableModel`.

## GameModel

The `GameModel` object is basically a container of other objects: a set of `PlayerModel`s, a `TableModel` and a `Suit` representing the trump card suit.

Every `PlayerModel` has a name and a hand, a `DeckModel` representing the cards that it has in its hand. A `DeckModel` is a simple List of `CardModel`s where it is possible to add or remove cards.

A `TableModel` contains a `DeckModel` representing cards that have been played on the table. It is possible to see those cards and to play another card. Currently, there are no restrictions about how many cards a player can play, but it is possible to add a `PlayingRule`: it is basically a lambda predicate specifying if a card can be played based on the card itself and other cards currently on the table.

The `Suit` object representing the trump card is trivial, and it is not used yet.
