# The below is the suggested way to invoke java with multiple jars in the classpath and target a Main
# java -cp .\application\target\application-1.0-SNAPSHOT.jar;.\GUI\target\GUI-1.0-SNAPSHOT.jar io.github.skenvy.sudokuGUI
# But using http://www.mojohaus.org/exec-maven-plugin/usage.html along with the additionalClasspathElements
# defined at http://maven.apache.org/surefire/maven-surefire-plugin/examples/configuring-classpath.html
# it's possible to just run it with a maven command.
cd ./GUI
mvn exec:java
cd ..
