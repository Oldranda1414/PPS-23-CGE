# Requirements

During project analysis the following requirements have been identified.

## Business Requirements

- The DSL should allow users to express card game rules concisely and intuitively.
- The DSL should be extensible to support various card game genres (e.g., trick-taking games, deck-building games).

## Domain Requirements

- The DSL must represent card decks, including standard playing cards (52 cards) and any custom decks.
- The DSL should handle card ranks (e.g., Ace, King, Queen) and suits (e.g., Hearts, Spades).
- Users can define custom card decks by designing custom ranks and suits.
- Users can express game rules (e.g., how cards are played, winning conditions) using a concise syntax.

## Functional Requirements

### User Functional Requirements

- Users should be able to easily define custom card sets and rules
- Users should be able to define game rules such as:
  - deck composition (seeds and ranks);
  - card value for hand winning;
  - trump selection through player decision;
  - seed based card play restrictions;
  - win condition:
    - number of hands won;
    - score bas- draw decks and draw rules;
  - card play/discard;
  - play cards to the table in other ways than simply playing a hand, such as:
    - placing them on the table;
    - giving them to other players;ed on cards won;

### System Functional Requirements

- The system should validate whether a played card follows the game rules.
- The system should manage the game state (e.g., player turns, scores).

## Non-Functional Requirements

- Readability: The DSL syntax should be human-readable and expressive.
- Modularity: The DSL should allow composing rules from reusable components (e.g., reusable card effects).
- Performance: The DSL library should execute rules efficiently during gameplay.
- Error Handling: The DSL should provide clear error messages for invalid rule definitions.

## Implementation Requirements

- Use of Scala 3.x.
- Use of JDK 11+.
- The DSL should be implemented as an internal DSL in Scala.
- The DSL should use Scala’s expressive syntax to create a natural language-like experience.
- The DSL should provide a clear separation between the DSL definition and the game logic.
- The codebase should be well-organized, documented, and maintainable.

[Back to index](../index.md) | 
[Previous Chapter](../development_process/index.md) | 
[Next Chapter](../architectural_design/index.md)
