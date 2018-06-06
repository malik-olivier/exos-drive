#!/bin/bash
test -f junit-4.12.jar || wget http://central.maven.org/maven2/junit/junit/4.12/junit-4.12.jar
test -f hamcrest-core-1.3.jar || wget http://central.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar Test*.java
java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar:../lib/sqlite-jdbc-3.20.0.jar org.junit.runner.JUnitCore TestUserJDBC
