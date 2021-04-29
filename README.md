# Testing the Wolfram Language integration in Java

Three small examples on using the Wolfram Language from within Java.

You need to have installed a Wolfram Engine, which means you need either

 1. The free Wolfram Engine, which can be obtained from [Free Wolfram Engine for Developers](https://www.wolfram.com/engine/) 
 2. A Raspberry Pie, 
 3. or another Wolfram product

As this is very much a proof of concept, some things are hardcoded. Look for this in the Java classes 😁. 

The three examples are as follows.

## RandomWords
Prints random words to stdout for as long as you want, which is hardcoded in the class. 

## SetAndGet

Define some symbols in the Wolfram Language and use these later in the class.

This example can be run by executing the Maven life cycle `package` and then this command:

    java -classpath WLTest-1.0-SNAPSHOT.jar:/Applications/Mathematica.app/Contents/SystemFiles/Links/JLink/JLink.jar SetAndGet

You need to substitute the location of the JLink.jar from your own installation of the Wolfram Engine.

## JSONTest

Generate some JSON in Wolfram Language and use it in Java. First example just by printing as string.