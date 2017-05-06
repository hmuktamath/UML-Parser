# UML-Parser

Refer to the pdf file for more details https://github.com/muktamath81/UML-Parser/blob/master/UML%20Parser%20Readme.pdf

Java Parser UML Generator

Initial setup -
• Install Java and use any available IDE for development. In my project, I have used Eclipse and to manage dependencies Maven.
• GraphViz must be installed on the system. It does not have plugins, hence it has to be installed (Link- http://www.graphviz.org/Download..php)

Instructions to execute the program
1) Download the package
2) Download the test files (test*.zip) and unzip it to a location (here – sourcefilepath)
3) Execute the code as below
• java –jar <jar file/Renamed file> <sourcefilepath> <outputfilenameandpath>
◦ sourcefilepath - Path to the default package folder.
◦ outputfilenameandpath – filename and path of the output image


External Libraries and Tools used
1. Javaparser
Javaparser is lightweight and easy to use parser library which parses java code and provides AST (Abstract Syntax Tree). Javaparser uses javacc (Java Compiler Compiler) for generating AST from Java code. One can analyze code structure, Javadoc or comments using AST created by Javaparser library.GraphViz is open source graph visualization software. GraphViz supports dot(.) notation input for drawing directed graphs. The GraphViz software takes input 
as simple text file and converts it to diagram. GraphViz provides support for generating output diagram in PDF, Images or SVG format. It has many useful features such as custom coloring, shapes and custom messages.

2. GraphViz
Limitation: GraphViz application is stand-alone application and can not be integrated in java project without third party library.

3. PlantUML
PlantUML provides a Java based API for using GraphViz software through your Java application. This library is required because GraphViz does not have their 
Java API exposed which developers can use to integrate GraphViz directly in their application. As PlantUML uses GraphViz internally, most of the functions that are offered by GraphViz are supported by PlantUML.

