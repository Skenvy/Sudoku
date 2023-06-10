# [Sudoku](https://github.com/Skenvy/Sudoku)
<!---
For a workflow status badge, see https://docs.github.com/en/actions/monitoring-and-troubleshooting-workflows/adding-a-workflow-status-badge
For a JavaDoc badge, see https://javadoc.io/
For a "Maven Project" badge, see https://github.com/softwaremill/maven-badges
--->
[![Java CI with Maven](https://github.com/Skenvy/Sudoku/actions/workflows/build.yaml/badge.svg)](https://github.com/Skenvy/Sudoku/actions/workflows/build.yaml)
[![javadoc](https://javadoc.io/badge2/io.github.skenvy/sudoku/javadoc.svg)](https://javadoc.io/doc/io.github.skenvy/sudoku)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.skenvy/sudoku/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.skenvy/sudoku)

Application of [specific techniques](https://sudoku.com/sudoku-rules/) to the solving of [sudokus](https://en.wikipedia.org/wiki/Sudoku), implemented in [Java](https://www.java.com/) ([OpenJDK](https://openjdk.org/)).
## Getting Started
### Using the executable JAR
[latest-jar]: https://repo1.maven.org/maven2/io/github/skenvy/sudoku/0.2.0/sudoku-0.2.0.jar
Starting from release `v0.2.0`, the released JAR will be an executable JAR targetting version 8, which will run the default 9*9 sized sudoku. Most recent JAR [_here_][latest-jar]. To build against a different version, you can;
```sh
git clone https://github.com/Skenvy/Sudoku.git && cd Sudoku
mvn clean package -Dmaven.compiler.release <desired_release_version>
```
### Source distribuitons
To install the latest from [Maven Central](https://repo1.maven.org/maven2/io/github/skenvy/sudoku/) source (mirror [sonatype.org](https://s01.oss.sonatype.org/content/repositories/releases/io/github/skenvy/sudoku/)) (also see the package indexes, [mvnrepository](https://mvnrepository.com/artifact/io.github.skenvy/sudoku) or, [sonatype](https://search.maven.org/artifact/io.github.skenvy/sudoku), or [_search_](https://search.maven.org/artifact/io.github.skenvy/sudoku));
### Add to the pom `<dependencies>`
```xml
<dependency>
  <groupId>io.github.skenvy</groupId>
  <artifactId>sudoku</artifactId>
</dependency>
```
### Or in gradle
```
implementation 'io.github.skenvy:sudoku'
```
## Usage
<TODO>

## [Maven-Site generated docs](https://skenvy.github.io/Sudoku/)
## [JXR generated XRef docs](https://skenvy.github.io/Sudoku/xref/index.html) | [_Test XRef_](https://skenvy.github.io/Sudoku/xref-test/index.html)
## [JavaDoc generated docs](https://skenvy.github.io/Sudoku/apidocs/io/github/skenvy/package-summary.html) | [_Test JavaDoc_](https://skenvy.github.io/Sudoku/testapidocs/io/github/skenvy/package-summary.html)
## [Checkstyle generated docs](https://skenvy.github.io/Sudoku/checkstyle.html)
## What it is at the moment
An implementation of a Sudoku solver (currently with Java JFrame), that I made during uni, as the first project I really enjoyed, with the intent of formalising how easy a particlar sudoku puzzle would be to solve by determining what moves can be made deterministically, rather than just iterating guesses.
## What I'd like to make it
The goal of this is to eventually create something similar to [opensudoku](https://github.com/romario333/opensudoku), or the [newer, current](https://github.com/ogarcia/opensudoku), version of it, as well as putting it on the playstore [similar to ~](https://play.google.com/store/apps/details?id=org.moire.opensudoku), with the inclusion of the originally intended feature/purpose of the JFrame app of determining available moves and how complex a particular game is.
## Developing
### The first time setup
_There is no one time setup required as each maven command will dynamically fetch its dependencies._ All you need to do is fork / clone it;
### Iterative development
* `make run` is the simplest test, compiling and executing the JFrame GUI.
* `make test` will run the JUnit tests.
* `make lint` will evaluate the [Checkstyle rules](https://github.com/Skenvy/Sudoku/blob/main/checkstyle.xml).
* `make docs` will create the site and then run it on [localhost](http://localhost:8080) with [javadoc here](http://localhost:8080/apidocs/io/github/skenvy/package-summary.html), and the [Checkstyle report here](http://localhost:8080/checkstyle.html).
### Run on Desktop
On any setup that has **make**, `make run` will be your friend. If you are in an environment where you can't install `make`, its recipe should be `mvn clean compile exec:java`.
## [Open Source Insights](https://deps.dev/maven/io.github.skenvy%3Asudoku)
