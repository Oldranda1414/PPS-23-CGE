# Introduction

- [Example](#example)
- [DSL Syntax](#dsl-syntax)
  - [Game Name](#game-name)
  - [Players](#players)
    - [Player Name](#player-name)
    - [Starting Player](#starting-player)
    - [Starting Hand](#starting-hand)
  - [Cards](#cards)
    - [Suits And Ranks](#suits-and-ranks)
    - [Trump](#trump)
  - [Game Rules](#game-rules)
  - [Final Line](#final-line)

The Card Game Engine is a powerful domain-specific language (DSL) designed to empower its users to easily define a given card game using easy to read syntax.

## Example

The following is an example of the game marafone defined with the Card Game Engine DSL:

```scala
    game is "Marafone"
    game has player called "Player 1"
    game has player called "Player 2"
    game has player called "Player 3"
    game has player called "Player 4"
    game gives 10 cards to each player
    game starts from player "Player 1"
    game suitsAre ("Batons", "Coins", "Cups", "Swords")
    game ranksAre ("Seven", "Six", "Five", "Four", "Knave", "Knight", "King", "Ace", "Two", "Three")
    game trumpIs "Batons"
    game playing rules are:
      (table, player, playerCard) => 
        player.hand.cards.contains(playerCard) &&
        (table.cardsOnTable.isEmpty || 
        (table.cardsOnTable.head.suit == playerCard.suit || player.hand.cards.filter(_.suit == table.cardsOnTable.head.suit).isEmpty))
        
    game hand rules are:
      (hand, cardPlayed, trump, ranks) => 
        val handSuit = hand.head.suit
        val t = trump.getOrElse("")
        val trumpsInHand = hand.filter(_.suit == t)
        val leadingSuitsInHand = hand.filter(_.suit == handSuit)
        val winningCard = if (trumpsInHand.nonEmpty) then
          trumpsInHand.maxBy(c => ranks.indexOf(c.rank))
        else
          leadingSuitsInHand.maxBy(c => ranks.indexOf(c.rank))
        cardPlayed == winningCard
    game win conditions are:
      (g, p) => 
        val nordSudCouple = List(g.players(0), g.players(2))
        val eastWestCouple = List(g.players(1), g.players(3))
        val playerCouple = if nordSudCouple.contains(p) then nordSudCouple else eastWestCouple
        val enemyCouple = if nordSudCouple.contains(p) then eastWestCouple else nordSudCouple
        playerCouple.map(_.points).sum > enemyCouple.map(_.points).sum
    GameController(game.build).startGame
```

## DSL Syntax

### Game Name

The game name can be defined as follows:

```scala
game is "<gameName>"
```

Where \<gameName\> is the name of the game.

### Players

#### Player Name

A player can be defined as follows:

```scala
game has player called "<playerName>"
```

Where \<playerName\> is the name of the player.

The game can have from 1 to 4 players.

#### Starting Player

The starting player is defined as follows:

```scala
game starts from player "<playerName>"
```

Where \<playerName\> is the name of the starting player.

The starting player can only be defined after defining the players' names.

#### Starting Hand

The number of cards in the starting hand of every player can be defined as follows:

```scala
game gives <numberOfCards> cards to each player
```

Where \<numberOfCards\> is the number of cards in hand for every player.

Also the game can also be defined to give a random number of cards to every player as follows:

```scala
game gives random cards to each player
```

### Cards

#### Suits And Ranks

The cards' ranks and suits can be defined as follows:

```scala
game suitsAre ("<suit1>", "<suit2>", "<suit3>", "<suit4>")
game ranksAre ("<rank1>", "<rank2>", "<rank3>", "<rank4>")
```

Where:

- \<suit[1..4]\> is a card suit
- \<rank[1..4]\> is a card rank

As a good practice ranks' order defines their relative strenght.

This means that the example defines the \<rank4\> to be the strongest rank and \<rank1> the weakest.

#### Trump

The game trump can be defined as follows:

```scala
game trumpIs "<trumpSuit>"
```

Where \<trumpSuit\> is the suit selected as trump.

The trump suit must be defined after defining the game suits.

The trump suit definition is not mandatory.

### Game Rules

Game rules are defined as follows:

```scala
game playing rules are:
    <rule1>, <rule2>, <rule3>
```

```scala  
game hand rules are:
    <rule1>, <rule2>, <rule3>
```

```scala
game win conditions are:
    <rule1>, <rule2>, <rule3>
```

Where \<rule1\>\<rule2\>\<rule3\> are Scala 3 lambdas that define the game rules.

See the [game definition example](#example) for rules parameters.

### Final Line

Every CGE game definition must end with the following line to run the game.

```scala
GameController(game.build).startGame
```

[Back to index](../index.md) |
[Next Chapter](../2_development_process/index.md)
