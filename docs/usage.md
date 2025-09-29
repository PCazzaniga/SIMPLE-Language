# How to use SIMPLE

Example:

1) Create a file _HelloWorld.simple_ in your work directory.
2) Open _HelloWorld.simple_ in your plain text editor of choice.
3) Copy and paste the code below:[^note]
    ```
   %%Example of a SIMPLE program that says "Hello, World!%%
   
   Define a procedure with name Main:
        Set Output to value of "Hello, World!",
        Return Nothing.

    ```
4) Open a command prompt / terminal in the directory.
5) Enter `simplexe HelloWorld.simple -e`.
6) The result should look like:
    ```
   >simplexe HelloWorld.simple -e
   Validation successful
   Hello, World!
   >
    ```
You can enter `simplexe -h` in the terminal to see all the options for the command.

[^note]: Make sure the tabulation characters `    ` are not replaced with multiple spaces ` ` ` ` ` ` ` `.
