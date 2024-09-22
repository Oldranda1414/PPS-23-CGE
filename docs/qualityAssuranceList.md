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
- It works flawlessly with the Metals plugin in VSCode installed. Using `Alt + Shift + F` will automatically update accordingly to the provided instruction in the `.scalafmt.config` file (it will take a bit. Let it cook)
- It's still possible to do it manually using the following commands:
    - `./gradlew scalafmt` formats your scala and sbt source code (main sourceset only)
    - `./gradlew checkScalafmt` checks whether all files are correctly formatted, if not, the task fails (main sourceset only)
    - `./gradlew testScalafmt` formats your test scala code based on the provided configuration
    - `./gradlew checkTestScalafmt` checks whether your test scala code is correctly formatted
    - `./gradlew scalafmtAll` formats scala code from all source sets
    - `./gradlew checkScalafmtAll` checks formatting of all source sets
- Bug: I found that if you try to call twice the same gradle task for scalafmt it will ignore the second one and return ok by default. I currently don't know if there is a solution.

## Wartremover
Discovered things:
- it should be better to use the library implemented by jharim but it requires scala3
- in order to update libs.scala.library version .toml file must be changed