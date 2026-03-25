After compiling the code, I tried to run the program by executing the following command in the terminal:
```
java .\MyFirstApp.class
```

Then I got the following error message:
```
Error: Could not find or load main class .\MyFirstApp.class
Caused by: java.lang.ClassNotFoundException: /\MyFirstApp/class
```

The java launcher expects the class name, not the file path or the .class extension. This causes the JVM to look for a class literally named .\MyFirstApp.class, resulting in ClassNotFoundException.

So, the correct command to run the program is:
```
java MyFirstApp
```
