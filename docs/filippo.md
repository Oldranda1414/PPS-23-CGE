# Qui metterò tutte le informazioni riguardanti il mio lavoro (in italiano)

## Overview

Il mio lavoro si concentra sull'implementare il DSL in modo che l'utente possa esprimere in linguaggio naturale definizioni, regole e comportamenti di un gioco di carte.

## Design

Partendo dal fatto che il model prevede un `GameModel`, ovvero una struttura dati che contiene tutte le informazioni necessarie per definire un gioco di carte, il DSL deve di fatto rappresentare un _builder_ per questa classe. Per builder si intende un oggetto in grado di costruire un'altra classe, in questo caso `GameModel`, in modo incrementale.

Essendo la sfida non banale si è deciso di suddividere il lavoro in due parti:

- `GameBuilder`: il vero e proprio builder che incapsula la logica per costruire un `GameModel`;
- `CardGameEngineDSL`: un elenco di _extension methods_ in grado di estendere la classe `GameBuilder` tramite parole il più vicine possibile al linguaggio naturale.

Un primo schema di massima viene riportato di seguito:

![Game Builder overview](./uml/DSL-Overview.png)

Volendo avvicinarci il più possibile al linguaggio naturale, i metodi all'interno del DSL non risultano espressivi se elencati in un diagramma delle classi come il precedente. Per capirne meglio le funzionalità, preferisco prima portare un esempio di codice che utilizzi il DSL e poi spiegare come è stato realizzato.

```scala
game is "Simple Game"
game has player called "Filippo"
game has player called "Andrea"
game has player called "Leonardo"
game gives 10 cards to each player
game starts from player "Filippo"
game suitsAre ("Bastoni", "Denari", "Spade", "Coppe")
game ranksAre ("Asso", "2", "3", "4", "5", "6", "7", "Fante", "Cavallo", "Re")
game trumpIs "Bastoni"
```

Ognuna di queste frasi doveva quindi chiamare uno o più metodi del `GameBuilder` in modo tale che fosse possibile costruire un `GameModel` in modo incrementale. Si è arrivati dunque alla soluzione seguente:

![DSL Game Builder](./uml/DSL-Game-Builder.png)

## Implementazione

Il design è partito proprio da questo tipo di frasi e da qui si sono sviluppati i concetti chiave.

Sin da subito si è notato che c'era bisogno di tanti costrutti che facessero da riempitivi, in modo tale da fare frasi di senso compiuto, anche se di fatto non aggiungevano informazioni al modello. Tali costrutti sono stati raggruppati all'interno della categoria `SyntacticSugar`.

Ne sono stati costruiti due tipi:

- `SyntacticBuilders` - classi con uno o pochi metodi che servono a costruire parti di frasi (similmente a come fa il _fluent interface pattern_);
- `SyntacticSugars` - oggetti definiti come impliciti e senza metodi, utilizzati come parametri da passare ai metodi del DSL.

La base da cui parte ogni cosa è `game`.
