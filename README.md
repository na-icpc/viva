# viva
This repository was created with the GitHub Importer tool from https://sourceforge.net/p/vanb-viva/code/HEAD/tree/

The first question here is how to build this thing.

I installed [JavaCC](https://javacc.github.io/javacc/) then did
```
cd src/org/vanb/viva/parser/
javacc PatternParser.jj
```
then this worked
```
mkdir compile
cd src
find . -name '*.java' | xargs javac -d ../compile -cp ../miglayout15-swing.jar
```

This will leave all `.class` files in the `./compile` folder.

Now
```
java -cp ./compile org.vanb.viva.VIVA
```
will invoke the tool.

Next: build a `.jar` file.
