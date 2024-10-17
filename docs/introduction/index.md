# Introduction

The Card Game Engine is a powerful domain-specific language (DSL) designed to empower its users to easily define a given card game using easy to read syntax.

```scala
game is "Simple Game"
game has player called "Andrea"
game has player called "Filippo"
game has player called "Leonardo"

game gives 5 cards to each player
game gives random cards to each player

game suits are ( Clubs, Diamonds, Hhearts, Spades ) 
game values are 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A 
game trump is clubs

```

[Back to index](../index.md) |
[Next Chapter](../development_process/index.md)
