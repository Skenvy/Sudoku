REM The below is the suggested way to invoke java with multiple jars in the classpath and target a Main
REM java -cp .\application\target\application-1.0-SNAPSHOT.jar;.\GUI\target\GUI-1.0-SNAPSHOT.jar io.github.skenvy.sudokuGUI
REM But using http://www.mojohaus.org/exec-maven-plugin/usage.html along with the additionalClasspathElements
REM defined at http://maven.apache.org/surefire/maven-surefire-plugin/examples/configuring-classpath.html
REM it's possible to just run it with a maven command.
cd ./GUI
call mvn exec:java
cd ..
