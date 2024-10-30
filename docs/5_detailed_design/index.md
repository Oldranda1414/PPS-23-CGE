# Detailed Design

- [DSL](#dsl)
- [Engine](#engine)

## DSL

The DSL uses a builder pattern to interact with engine (explain better pls).

TODO small uml of dsl structure

## Engine

As previously stated the Engine uses the Model-View-Controller pattern.

The Controller and View communicate using a publish-subscribe pattern. The View publishes events, which correspond to user interactions. The Controller will then subscribe to the events and react by updating the Model and the View accordingly.

Java Swing is used as a technology for the view implementation.

TODO small uml of the engine

[Back to index](../index.md) |
[Previous Chapter](../4_architectural_design/index.md) |
[Next Chapter](../6_implementation/index.md)
