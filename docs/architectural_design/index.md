# Architectural design
The project can be divided into three main parts:

- **DSL**: it handles the DSL's custom syntax and changes the internal model state;
- **Engine**: it models the card game, based on what the user specified using the DSL;
- **User interface**: it provides a way of playing the spceified game;
