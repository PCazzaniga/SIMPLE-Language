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

[^note]: Make sure the tabulation characters `    ` are not replaced with multiple spaces ` ` ` ` ` ` ` `.

## Command options

The `simplexe` command has several options that can be entered alongside it, after the file name:
- `--help` Prints a helper message that briefly describes the command and its options.
- `--args` To pass anything after it as arguments for the [Main](manual.md#main) function of the program.
- `--dialect` To specify which grammar dialect the code is written with (ENG, ESP, FRA, ITA or ALT).
- `--execute` To execute the .simple file after its validation.
- `--loop` To set the maximum times conditional [loops](manual.md#loop-statement) can iterate. Default is 100.
- `--minimal` To reduce the amount of output produced by the interpreter and not by the program itself.
- `--recursion` To set the maximum times functions can be [called](manual.md#function-call) concurrently. Default is 100.

All options have both longhand and shorthand versions, e.g. `--help` can also be used as `-h`.  
Order of options doesn't matter except for `--args` which if present must, obviously, be last.  
`--args`, `--dialect`, `--loop` and `--recursion` require to be followed immediately by opportune values. 