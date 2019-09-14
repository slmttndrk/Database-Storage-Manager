# Database Storage Manager

## INTRODUCTION

  In this repository, I implemented a database storage manager. I created two java files 
that are "storageManager.java" and "TypesAndRecords.java". The main class is in the storageManager.java. 
When you run the program, first it takes two command line arguments that are "inputfile.txt"
for instructions to be operated and "outputfile.txt" for results to be showed.
Then it splits each lines in the input file as a separate instruction. Then , the
program applies all the instructions one by one. In this project, there are
eight operations that are "Create a type", "Delete a type", "List all types",
"Create a record", "Delete a record", "Update a record", "Search for a record"
and "List all records of a type". 

## IMPLEMENTATION

Enter into the directory, then type these commands into the terminal:

  * javac dbsm/src/*.java
  * java -classpath dbsm/src/ storageManager inputFile.txt outputFile.txt

When the program run, the SystemCatalog and .data files will be created according to instructions.
I also added two sample files in order to demonstrate the parameters.
