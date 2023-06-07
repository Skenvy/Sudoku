# [Sudoku](https://github.com/Skenvy/Sudoku)
Application of specific techniques to the solving of [sudokus](https://en.wikipedia.org/wiki/Sudoku), implemented in [Java](https://www.java.com/) ([OpenJDK](https://openjdk.org/)). Also see [Mathematics of Sudoku](https://en.wikipedia.org/wiki/Mathematics_of_Sudoku).
## Getting Started
[To install the latest from Maven Central](https://repo1.maven.org/maven2/io/github/skenvy/sudoku/) ([sonatype.org source mirror](https://s01.oss.sonatype.org/content/repositories/releases/io/github/skenvy/sudoku/)) (also see [mvnrepository](https://mvnrepository.com/artifact/io.github.skenvy/sudoku) or the [sonatype package index](https://search.maven.org/artifact/io.github.skenvy/sudoku));
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
## [JavaDoc generated docs](https://skenvy.github.io/Sudoku/apidocs/io/github/skenvy/package-summary.html)
## What it is at the moment
An implementation of a Sudoku solver (currently with Java JFrame), that I made during uni, as the first project I really enjoyed, with the intent of formalising how easy a particlar sudoku puzzle would be to solve by determining what moves can be made deterministically, rather than just iterating guesses.
## What I'd like to make it
The goal of this is to eventually create something similar to [opensudoku](https://github.com/romario333/opensudoku), or the [newer, current](https://github.com/ogarcia/opensudoku), version of it, as well as putting it on the playstore [similar to ~](https://play.google.com/store/apps/details?id=org.moire.opensudoku), with the inclusion of the originally intended feature/purpose of the JFrame app of determining available moves and how complex a particular game is.
## Developing
### The first time setup
_There is no one time setup required as each maven command will dynamically fetch its dependencies._
### Iterative development
* `make test` will do nothing magical, but is helpful
* `make docs` will create the site and then run it on [localhost](http://localhost:8080) with [javadoc here](http://localhost:8080/apidocs/io/github/skenvy/package-summary.html).
### Run on Desktop
On any setup that has **make**, `make run` will be your friend. Otherwise, `mvn clean compile exec:java` will do the same job.
## [Open Source Insights](https://deps.dev/maven/io.github.skenvy%3Asudoku)
