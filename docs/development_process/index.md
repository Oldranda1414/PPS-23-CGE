# Development Process

## Scrum

The development process choosen for this project is SCRUM([SCRUM Primer](https://scrumprimer.org/)), an agile project management framework.

As per agile development, SCRUM defines a loose set of activities that enable the development team to embrace an iterative and incremental process.

The development process will be devided sprints of the lenght of one week each. Every sprint starts with a meeting, called sprint planning, where items from the product backlog will be selected to be implemented and expandend in multiple items, adding up to form the sprint backlog. The team members volontier to work on a given item and progress is tracked in the sprint backlog.

At the end of a given sprint a meeting takes place with 3 goals:

- Product Backlog Refinement;
- Sprint Review;
- Sprint Retrospective;

Members of the development team will also roleplay as:

- Product Owner (Leonardo Randacio) : responsible for maximizing the value brought by the product.
- Client (Filippo Gurioli) : the eventual buyer and founder for the project, responsible for ensuring it meets the requirements.

Dialy SCRUM meetings have been ditched for efficient indipendent work. A meeting will take place once a week to close the previous sprint and to start the next one.

[_Trello_](https://trello.com/) will be used for coordination, while meeting will take place through [Google Meet](https://meet.google.com/) videocalls

## Test-Driven Development

Test-Driven Development(TDD) will be followed during code production throughout the project.

TDD is a software development approach here developers write tests for a feature or function before implementing the actual code. The process follows a simple cycle, often referred to as "Red-Green-Refactor":

- Red (Write a failing test): Write a test for a specific functionality that doesn't exist yet. Since the functionality hasn't been implemented, the test will fail.
- Green (Write the minimal code to pass the test): Write just enough code to make the test pass. The focus here is on passing the test, not on perfect code.
- Refactor (Improve the code): Once the test passes, refactor the code to improve its structure, readability, and maintainability while ensuring the tests continue to pass.

## Git Flow

Git flow branching model will be used.

Two main branches will always exist:

- `master` : for stable releases;
- `develop` : for feature integration;

Feature branches can be created, branching from the `develop` branch, to implement new fetures. Once a new feature is implemented it should be merged into the `develop` branch. To create a new release the `develop` branch is merged into the `master` branch.

Hotfix and fix branches can be branched from the master and developd branches accordingly for bug fixes.

[gitflow](https://pypi.org/project/gitflow/) command line tool will be used for high-level repository operations

## Commit Standardization

Commit messages must follow a standardized form:

```none
<type>: <short summary>
```

Where `type` is one of the following types:
    - feat: A new feature is introduced to the codebase;
    - fix: A bug fix;
    - docs: Documentation updates only;
    - style: Changes related to formatting, like spacing or indentation, that don’t affect the code’s logic;
    - refactor: Code changes that neither fix a bug nor add a feature, typically done to improve the structure;
    - test: Adding or updating tests;
    - chore: Routine tasks like updating dependencies or configuration;
    - perf: Code changes aimed at improving performance;

and `short summary` is a short summary of the commit contents.

## Quality Assurance

<!-- todo -->

## Build Automation

<!-- todo -->

## Continuous Integration

To ensure code correctness and integrity a Continuous Integration pipeline has been setup, using Github Actions, to execute the test on various operative systems every code update.

## Automated Evolution

[Renovate Bot](https://github.com/renovatebot) has been included in the project to automate dependency updates

[Back to index](../index.md) |
[Previous Chapter](../introduction/index.md) |
[Next Chapter](../requirements/index.md)
