# Detailed Design

- [DSL](#dsl)
- [Engine](#engine)
  - [GameModel](#gamemodel)
  - [GameView](#gameview)

## DSL

The main goal of the DSL part is to let the user specify parameters and settings,
and to prepare a `GameBuilder` with them.

The following UML diagram illustrates the main architecture of the DSL part.

![dsl](../uml/ad_dsl.png)

`CardGameEngineDSL` represents the entry point for every DSL sentence: using
some pre-defined *words* from `SyntacticSugar` and providing specific builders
to lock sentences syntax, it is possible to create a chain of commands changing
the builder's internal state. The implementation phase is developed as a direct
extension of the design section: each element defined here serves as a clear
guide for implementation.

## Engine

Java Swing is used as technology for the view implementation.

Below the UML diagram of the engine.

![engine](../uml/engine.png)

### GameModel

The Model defines two base concepts:

- `TableModel` : rappresents the table, where cards are placed once played
- `PlayerModel`

### GameView



[Back to index](../index.md) |
[Previous Chapter](../4_architectural_design/index.md) |
[Next Chapter](../6_implementation/index.md)
