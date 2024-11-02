# Conclusion

- [Source Code Future Improvments](#source-code-future-improvments)
- [Git flow](#git-flow)
- [Agile](#agile)
- [TDD](#tdd)
- [CI](#ci)

## Source Code Future Improvments

The second rule extension was only partially implemented due to time contrains.

The testing could be extended to comprehend the Controller and View modules of the project.

The current form of the game rules definition in the DSL are implemented as plain scala lambdas. This means that the user would have to understand the structure of the game Model in order to define rules. This could be improved by implementing keywords for the most common rules present in card games enabeling the DSL to be more expressive and ensurng the user does not need to have knowledge of the engine's implementation.

## Git flow

The team is happy of how git flow was used during project development although often feature branches where merged with the develop branch before the feature was complete due to misunderstandings on feature completenes between team members.

## Agile

The team tried to adopt the SCRUM metodology since the start of the project. During the projects development the team noticed improvements in adhering to agile processes as time went by.

The project proved that understanding Agile development principles is not enough to put them in practice and much experience is necessary to fully adopt a SCRUM development metodology.

The project experience highlighted the need of a SCRUM Master.

Due to misunderstandings between team members some sprints ended with features half implemented, proving the need for frequent meetings between team members even during the sprint or for improved sprint planning.

## TDD

The team tried to adhere to TDD during the project, although often it resulted in frustration as class and method structures varied often, especially during the first few sprints.

This highlighted the need for a deeper design phase and experience using all techonologies involved.

## CI

Continuous Integration has been apprecieted by all team members as automating keeping track of test coverage and testing every release on every target platform proved very helpful and efficient, letting the team members focus on feature development.

[Back to index](../index.md) |
[Previous Chapter](../7_testing/index.md)
