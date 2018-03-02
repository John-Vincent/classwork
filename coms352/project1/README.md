
~COMPILING/RUNNING~

to compile the program run make.

to run the program either run the executable osh located in bin/ after make is run
  or run the command "make run" which will compile then run the program.

the make file assumes you are running on a standard unix system with the command "mkdir -p" being present to
  create the bin and bin/objects folders in the project directory. If mkdir is non-existant than manually
  create the folders, remove the commands under "makebin" make rule and run make.
  it also assumes that gcc is present and the pthread and rt libraries exist, if this is not true,
  then change the C_COMP variable to whatever compiler you have and the C_LINKS variable to the
  equivalent libraries on your machine.


~DOCUMENTATION~
Functions that have a declaration in a header file will have their documentation in the header file to save space in the c files.

Functions that are not shared with other files through headers will have the documentation in the c file itself.
