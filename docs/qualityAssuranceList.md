# Quality Assurance List

## Style and coherence

Scala formatter  
Wart Remover  
Scala style  

## flawed programming patterns

Scala Fix  
Wart Remover  

## DRY violation spotters

Copy/Paste Detector - CPD (part of PMD)

## Testing and coverage
kinds:
 - unit
 - integration
 - end-to-end

Scalatest  
Scoverage - scala specific  

## Gradle for QA

test: test tasks  
check: task that runs the whole QA suite

---

## Scoverage
Doc: [Scoverage](https://github.com/scoverage/gradle-scoverage)
- We should specify in the build.gradle.kts file the minimum coverage percentage that we want to achieve (line 54).
- We should also specify the directories that we want to exclude from the coverage analysis (line 55).
- To execute it: `./gradlew reportScoverage` - to run only the unit tests and no other test tasks (e.g., integration tests): `reportTestScoverage`
- To check the data produced by the coverage analysis: `./gradlew checkScoverage`
- It's also possible to check scoverage reports in the browser: open the file `build/reports/scoverage/index.html`

## Scalafmt
Doc: [Scalafmt](https://scalameta.org/scalafmt/docs/installation.html)
- It should work flawlessly with the Metals plugin in VSCode installed.