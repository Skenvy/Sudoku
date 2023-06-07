# [What it started as](https://github.com/Skenvy/Sudoku/releases/tag/v0.0.0)
An [implementation of a Sudoku solver](https://github.com/Skenvy/Sudoku/blob/v0.0.0/Sudoku/APP/src/main/java/com/skenvy/sudoku/sudoku.java), [utilising JFrames](https://github.com/Skenvy/Sudoku/blob/v0.0.0/Sudoku/GUI/src/main/java/com/skenvy/sudokuGUI/sudokuGUI.java), that I made during uni in my second year (2013), as the first project I really _enjoyed_, because it's easier to learn programming if you're using it to build something that has a purpose, as opposed to throw-away tutorial code.

I made it with the intent of formalising how easy a particlar sudoku puzzle would be to solve by determining _what moves can be made_, deterministically, rather than just iterating guesses.

See [the code as close to as it originally was that I could find](https://github.com/Skenvy/Sudoku/tree/v0.0.0), or [the release of that version](https://github.com/Skenvy/Sudoku/releases/tag/v0.0.0).

# What I'd like to make it
The "goal" of this is to """eventually""" create something similar to [opensudoku](https://github.com/romario333/opensudoku), or the ~~[newer, current](https://github.com/ogarcia/opensudoku)~~ [newerer, currenterer](https://gitlab.com/opensudoku/opensudoku), version of it, as well as putting it on the playstore [similar to ~](https://play.google.com/store/apps/details?id=org.moire.opensudoku), with the inclusion of the originally intended feature/purpose of the JFrame app of determining available moves and how complex a particular game is. 

# Devlog
## Java Build Tools
Although as part of the eventual goal of using this project as a canvas to learn some of the components of the Android development tool chain, I'd like to eventually try and swap from Maven to Gradle, I've had to remove the old gradle files that were loosely hanging around, as GitHub's [CodeQL tool for scanning java projects](https://codeql.github.com/docs/codeql-overview/supported-languages-and-frameworks/#), [which currently includes kotlin](https://docs.github.com/en/code-security/code-scanning/automatically-scanning-your-code-for-vulnerabilities-and-errors/customizing-code-scanning#changing-the-languages-that-are-analyzed), will [autobuild using Gradle rather than Maven if it detects any Gradle files present](https://docs.github.com/en/code-security/code-scanning/automatically-scanning-your-code-for-vulnerabilities-and-errors/configuring-the-codeql-workflow-for-compiled-languages#java--and-kotlin).
## Linting
The code is pretty gross at the moment, so we'll need to add a linter. [VS Code's page on linting java](https://code.visualstudio.com/docs/java/java-linting) suggets [Checkstyle](https://checkstyle.sourceforge.io/) ([source](https://github.com/checkstyle/checkstyle)) ([plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/index.html)). The [Sun Java Style](https://checkstyle.org/sun_style.html) is the default style rule set. The other already included rule set is the [Google Java Style](https://checkstyle.org/google_style.html). The rule set for Sun Style are apparently enforcing Oracle's [Code Conventions for the Java Programming Language](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html) (from 20th April 1999???) and is using [this config](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/sun_checks.xml). The rule set for Google Style are enforcing a _much_ more recent set of rules, the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html), using [this config](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml). There exists the option to [use inline configuration](https://maven.apache.org/plugins/maven-checkstyle-plugin/examples/inline-checker-config.html), but it replaces entirely, rather than overrides inplace, any other specified config. For instance, the following block will _only evaluate against the LineLength rule_.
```xml
<configuration>
  <configLocation>sun_checks.xml</configLocation>
  <checkstyleRules>
    <module name="Checker">
      <module name="LineLength">
        <property name="max" value="240" />
        <property name="fileExtensions" value="java"/>
      </module>
    </module>
  </checkstyleRules>
</configuration>
```
Although Google's style appears more readily meaningful, the `sun_checks.xml` option yields around 3300-ish errors, as opposed to `google_checks.xml` which only yields around 6700-ish _warnings_, but no errors. If we want to actually override the settings provided, we'll need to copy one of the existing rule sets and edit it and check it in.
### Some quick find and replaces
A lot of the 3300-ish errors (according to the `sun_checks.xml`) are very similar. What are some quick find and replaces?
* Replace `){` with `) {`
* Replace `for(` with `for (`
* Add spaces around single `+` operators
    * Replace `(?<=[^\+ ])\+(?=[^\+])` with ` +`
    * Replace `(?<=[^\+])\+(?=[^\+ ])` with `+ `
    * Fix a whoopsie by replacing `\+ =` with `+=`
* Replace `\t` (tab) with "`    `" (four spaces)
* Halve all leading spaces indentation; (spaces followed by a letter, right curly, forward slash, or space and asterisk)
    * Replace `^ {4}(?=[\w}/]|( \*))` with "`  `" (two spaces)
    * Replace `^ {8}(?=[\w}/]|( \*))` with "`    `" (four spaces)
    * _etc._ (up to `^ {56}(?=[\w}/]|( \*))` with 28 spaces, apparently)
* Chomp all only whitespace lines;
    * Replace `^\s*$` with nothing.
* Replace `/\*\*\*` with `/**`
* Replace `if\(` with `if (`
* Use `(?<!\/\*|\/|^ |^   )\*` to find `*` that aren't part of some Javadoc's left most `*`;
    * Negates `^\s+\/**` and `^\s+*`
    * Fixed length `(?<!...)` only
        * Knowing we only have `^\s+*` at one or three indents.
* Add spaces around single `-` operators
    * Replace `(?<=[^- ])-(?=[^-=])` with ` -`
    * Replace `(?<=[^-])-(?=[^- =])` with `- `
* Add spaces around single `*` operators
    * Replace `(?<=[^/\* ])\*(?=[^/\*=])` with ` *`
    * Replace `(?<=[^/\*])\*(?=[^/\* =])` with `* `
* Add spaces around single `/` operators
    * Replace `(?<=[^/ \*])/(?=[^/\*])` with ` /`
    * Replace `(?<=[^/\*])/(?=[^/ \*])` with `/ `
* Replace `(?<! )&&` with ` &&`
* Replace `&&(?! )` with `&& `