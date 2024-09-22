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
Doc: [WartRemover](https://www.wartremover.org/doc/warts.html)
- It must be executed from shell in order to see its result.
- It's added in the scala build pipeline. This means that you can just launch `./gradlew build` command in order to see the result.
- I've set up some warts, the most took from Jahrim and some other decided by me. We should meet and decide together which we should enable/disable.

## Scalastyle
Doc: [Scalastyle](http://www.scalastyle.org/configuration.html)
- It must be executed from shell in order to see its result.
- It adds new tasks to scala build pipeline. This means that you can just launch `./gradlew build` command in order to see the result.
- I've set up some style rules. We should meet and decide together which we should enable/disable.
- There is the chance to disable on demand style check using specific comments within code (see comment filters in the doc).
!- I found that scalastyle is compatible in some way with sonarqube [link](https://scalastyle.beautiful-scala.com/)


## Scalafix
Doc: [Scalafix](https://scalacenter.github.io/scalafix/docs/rules/overview.html) [Scalafix-gh](https://github.com/cosmicsilence/gradle-scalafix)
- It must be executed from shell in order to see its result.
- It adds new tasks to scala build pipeline. This means that you can just launch `./gradlew build` command in order to see the result.
- It defines some custom commands:
    - `scalafix` Runs rewrite and linter rules for all source sets. Rewrite rules may modify files in-place whereas linter rules will print diagnostics to Gradle's output.
    - `checkScalafix` Checks that source files of all source sets are compliant to rewrite and linter rules. Any violation is printed to Gradle's output and the task exits with an error. No source file gets modified. This task is automatically triggered by the check task.

## WARNING
Wart remover behaves diffrently from scalastyle and scalafix. It's inserted directly into the scala building pipeline that means that no tasks will appear at the compilation.
Still there will be showed warnings/errors if some warts would be found. Instead, scalafix and scalastyle will show thier custom tasks working in the background during the compilation.