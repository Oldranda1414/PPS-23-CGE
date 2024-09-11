# Details

## Simple game

The user must be able to manipulate the following game rules:

- number of players;
- number of cards in hand per player (random or fixed);

The number of cards per player must sum up to the number of cards available, as no draw deck is accounted for in this version of the ruleset.

The win condition is fixed:
The player with the most amount of cards is the winner. A tie determines multiple winners.

## First ruleset expansion

A *hand* concept is introduced as a fixed rule. Every player plays a card to the table for the turn. Once every player has played a card the *hand* is finished and a player is chosen as the hand winner.

The user must be able to manipulate the following game rules, in addition to previous capabilities:

- deck composition (seeds and ranks);
- card value for hand winning;
- trump selection through player decision;
- seed based card play restrictions;
- win condition:
  - number of hands won;
  - score based on cards won;

The number of cards played per hand is fixed to one.

## Second ruleset expansion

The *hand* concept is now rendered optional and the user is able to define customized rules for card playing and *hands*.

The user must be able to manipulate the following game rules, in addition to previous capabilities:

- draw decks and draw rules;
- card play/discard;
- play cards to the table in other ways than simply playing a hand, such as:
  - placing them on the table;
  - giving them to other players;

## Demos

The user should have available the following premade demos:

- briscola ([rules](https://www.pagat.com/aceten/briscola.html))
- marafone ([rules](https://en.wikipedia.org/wiki/Marafon))
- goon president ([rules](goon_president.md#rules))

[Back to Product Backlog](product_backlog.md)
