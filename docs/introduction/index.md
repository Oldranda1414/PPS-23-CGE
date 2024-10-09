# Introduction

The Card Game Engine is a powerful domain-specific language (DSL) designed to empower its users to easily define a given card game using easy to read syntax.

```scala
Game is "Simple Game"
    has player called "Andrea"
    has player called "Filippo"
    has player called "Leonardo"
    players have a number of cards equals to 5

Game players have 3 cards in hand
Game players have random cards in hand

Game start
------------------------------------------------------------------------------
Game cards have 3 values called "one" "two" "three"
Game cards have 3 suits called "clubs" "hearts" "diamonds"
```

[Back to index](../index.md) |
[Next Chapter](../development_process/index.md)
