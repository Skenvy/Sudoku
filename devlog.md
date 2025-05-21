# Devlog
## [What it started as](https://github.com/Skenvy/Sudoku/releases/tag/v0.0.0)
An [implementation of a Sudoku solver](https://github.com/Skenvy/Sudoku/blob/v0.0.0/Sudoku/APP/src/main/java/com/skenvy/sudoku/sudoku.java), [utilising JFrames](https://github.com/Skenvy/Sudoku/blob/v0.0.0/Sudoku/GUI/src/main/java/com/skenvy/sudokuGUI/sudokuGUI.java), that I made during uni in my second year (2013), as the first project I really _enjoyed_, because it's easier to learn programming if you're using it to build something that has a purpose, as opposed to throw-away tutorial code.

I made it with the intent of formalising how easy a particlar sudoku puzzle would be to solve by determining _what moves can be made_, deterministically, rather than just iterating guesses.

See [the code as close to as it originally was that I could find](https://github.com/Skenvy/Sudoku/tree/v0.0.0), or [the release of that version](https://github.com/Skenvy/Sudoku/releases/tag/v0.0.0).

## What I'd like to make it
### What I wanted to make it in 2023
The "goal" of this is to """eventually""" create something similar to [opensudoku](https://github.com/romario333/opensudoku), or the ~~[newer, current](https://github.com/ogarcia/opensudoku)~~ [newerer, currenterer](https://gitlab.com/opensudoku/opensudoku), version of it, as well as putting it on the playstore [similar to ~](https://play.google.com/store/apps/details?id=org.moire.opensudoku), with the inclusion of the originally intended feature/purpose of the JFrame app of determining available moves and how complex a particular game is.
### What I've changed to wanting to make it into now
Originally, I thought it would be a good way to learn android app development. But that's less relevant now. If I can, I'd like to make it more widely distributable than that. Of course, making it the way that it currently is (albeit in an unfinished state) of running as an executable jar, provides for an already easily distributable program. I have since then thought that it might be a nice idea to try and learn a java game framework to make it more polished than just `swing` and `awt` do.

## Initial cleanup
### Java Build Tools
Although as part of the eventual goal of using this project as a canvas to learn some of the components of the Android development tool chain, I'd like to eventually try and swap from Maven to Gradle, I've had to remove the old gradle files that were loosely hanging around, as GitHub's [CodeQL tool for scanning java projects](https://codeql.github.com/docs/codeql-overview/supported-languages-and-frameworks/#), [which currently includes kotlin](https://docs.github.com/en/code-security/code-scanning/automatically-scanning-your-code-for-vulnerabilities-and-errors/customizing-code-scanning#changing-the-languages-that-are-analyzed), will [autobuild using Gradle rather than Maven if it detects any Gradle files present](https://docs.github.com/en/code-security/code-scanning/automatically-scanning-your-code-for-vulnerabilities-and-errors/configuring-the-codeql-workflow-for-compiled-languages#java--and-kotlin).
### Linting
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
<details>
<summary>Some quick find and replaces</summary>

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

</details>

## Publishing jars since the last time I uploaded this
The last time I published anything to maven central, I did so through [this sonatype nexus page](https://s01.oss.sonatype.org/). But on logging in to try and remember how to change my gpg key which expired a while ago, I'm met with a warning at the top about [OSSRH EOL on the 30th of June 2025](https://central.sonatype.org/news/20250326_ossrh_sunset/). It includes a link to the docs for the new publishing site, the [Central Portal publishing guide](https://central.sonatype.org/publish/publish-portal-guide/). It also includes a link to the [process for migrating](https://central.sonatype.org/faq/what-is-different-between-central-portal-and-legacy-ossrh/#process-to-migrate).

We can start this by going to the new [Central Portal](https://central.sonatype.com/) and signing in via github. Doing so, it tells me straight away that I have `No verified namespaces`, and includes a link to [an anchor that doesn't exist on the publishing guide](https://central.sonatype.org/publish/publish-portal-guide/#why-do-i-need-to-prove-domain-ownership-and-how-do-i-do-it). I go to [publishing namespaces](https://central.sonatype.com/publishing/namespaces) and enter my `io.github.skenvy` and it pops up a warning
> [!CAUTION]
> Namespace 'io.github.skenvy' is already registered as an OSSRH Namespace.
> For help with OSSRH namespaces, please refer to [our documentation](https://central.sonatype.org/publish-ea/publish-ea-guide/#existing-ossrh-namespaces). If you own this namespace on OSSRH and want to manage it through Central Portal, please read through [the differences between Central Portal and Legacy OSSRH](https://central.sonatype.org/faq/what-is-different-between-central-portal-and-legacy-ossrh/).
> 
> Once you have read the above documentation, please email [central-support@sonatype.com](mailto:central-support@sonatype.com) for help migrating.

After a few attempts trying other logins, it turns out that my old sonatype nexus login was already copied over to the new site, and it finally presents me with the automatic suggestion to "Migrate namespace" on my `io.github.skenvy` namespace. It would be handy if it had been able to recognise implicit ownership when logging in via github, given that each `io.github.*` can only be owned by the account of that name. But no matter, I've found it again, and just have to remember for next time that I have to login with credentials rather than via github.

I clicked to migrate my namespace, and it error'd once, then completed successfully the second time. But it doesn't show me my packages under my namespace. I can however just go [search for them](https://central.sonatype.com/search?namespace=io.github.skenvy).

How to change the current publishing CD step that publishes to OSSRH? The [publish portal guide](https://central.sonatype.org/publish/publish-portal-guide/#component-validation) links to the [requirements](https://central.sonatype.org/publish/requirements/) for publishing. I'll assume that they are all the same requirements already established for previously publishing to OSSRH. Then all we need to do it have a look at [publish portal maven](https://central.sonatype.org/publish/publish-portal-maven/). As well as this, I'll have to remember how to configure gpg for maven, [the new docs on expired keys](https://central.sonatype.org/publish/requirements/gpg/#dealing-with-expired-keys), along with where I first devlogged about setting up my maven gpg [here](https://github.com/Skenvy/Collatz/blob/main/java/devlog.md#:~:text=set%20up%20a%20gpg%20key%20for%20sonatype), and my expired key [here](https://keyserver.ubuntu.com/pks/lookup?search=F398EA6448A7708EAABBB0DEC203EA8449D06C1B&fingerprint=on&op=index).

## [libGDX](https://libgdx.com/)
The tentative plan going forward is to try and align what already exists with something that could be made into a libGDX built game.
At the moment I'm just dumping links here until I write this.
* [How to get started with libGDX?](https://libgdx.com/dev/#how-to-get-started-with-libgdx)
* [Creating a Project](https://libgdx.com/wiki/start/project-generation)
* [gradle](https://gradle.org/)
* [libgdx is still using java 8](https://github.com/libgdx/gdx-liftoff/blob/v1.13.5.0/build.gradle#L33-L34)
* [corretto-8](https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/downloads-list.html)

##### WIP NOTES TO REMOVE BEFORE MERGE
1. Reposition stuff in the repo so that there's two halves to it;
    * the part that packages the `swing`/`awt` based jar
    * and a new half for libGDX parallel to it that imports from it.
1. Find my old gpg key and try renew the old one.
1. Update the publishing in build to use the new publishing method.
