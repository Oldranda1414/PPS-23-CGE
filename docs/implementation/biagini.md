In the project implementation, I contributed working on:

- The `Game` object, representing the engine model

## Engine model
Inside the `Game` object, three traits have been created: `Card`, `Player` and `Deck`. The implementation has been done incrementally, starting from `Card`: a `SimpleCard` case class has been created, with simple strings as fields, modelling the card value and the card suit. After that, a `SimplePlayer` case class has been added, initially having a set of cards as private variable field; `addCard` and `cards` defs have been added to the trait. But after introducing the `Deck` trait, and implementing it with the `SimpleDeck` case class, the varible field inside `Player` has been replaced with a `Deck` object.

The current implementation of the game engine supports players having a deck of cards each.
