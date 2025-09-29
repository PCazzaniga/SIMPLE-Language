# Programming with S.I.M.P.L.E.

S.I.M.P.L.E. programs are written in `.simple` [files](#File) and then executed from terminal (see [usage.md](usage.md)).

Start learning from [here](#File) or go to the [index](#Index).

---

### Arithmetical operation
Syntax: `(`_X_` `_op_` `_Y_`)`.

_X_ and _Y_ can be:
- A [direct](#Direct-value) value.
- A [special](#Special-value) value.
- Another arithmetical operation.

_op_ can be:
- `+` for addition.
- `-` for subtraction.
- `*` for multiplication.
- `/` for division.
- `mod` for modulus/remainder.

### Assignment
Syntax: `Set `_X_` to value of `_Y_.

_X_ can be:
- The [name](#Name) of a variable.
- [Access](#Structure-access) to part of a structured variable.
- `Output`, to show the value of _Y_ on terminal.

_Y_ can be:
- A [direct](#Direct-value) or [special](#Special-value) value.
- An [arithmetical](#Arithmetical-operation), [logical](#Logical-operation) or [comparison](#Comparison-operation) operation.
- A [call](#Function-call) to a function, between `()`.
- `Input`, to get the value from terminal.

### Comment
A single line of text starting and ending with `%%`.

### Comparison operation
Syntax: `(`_X_` `_op_` `_Y_`)`.

_X_ and _Y_ can be:
- A [direct](#Direct-value) value.
- A [special](#Special-value) value.
- An [arithmetical](#Arithmetical-operation) operation.

_op_ can be:
- `>` for "greater than" comparison.
- `<` for "less than" comparison.
- `=` for equality comparison.

### Conditional statement
Syntax: `If `_X_` then:` followed by a newline and a [scope](#Scope).  
Can be followed by zero or more `Else if `_Y_` then:` and one or no `Else:`, each with its own scope.

_X_ and _Y_ can be:
- A [logical](#Logical-operation) operation.
- A [comparison](#Comparison-operation) operation.

### Direct value
Can be:
- A [literal](#Literal).
- The [name](#Name) of a variable, to use its value.
- [Access](#Structure-access) to part of a structured variable, to use its value.

### File
A file contains a list of:
- [Comments](#Comment).
- Declarations of [variables](#Variable-declaration), [types](#Type-declaration) or [functions](#Function-declaration), each terminated by `.`.

Everything has to be separated by one or more empty lines.  
The very first line of the file **cannot** be empty.

### Function call
Syntax: `Execute procedure `[name](#Name).  
Can be followed by ` with ` and one or more [direct](#Direct-value) values separated by `, ` to pass arguments to the function.

### Function declaration
Syntax: `Define a procedure with name `[name](#Name).   
Can be followed by:
- ` that produces a `[type](#Type), if the function [returns](#Return) some value.
- ` that uses a ` followed by one or more [type](#Type)` `[name](#Name) separated by `, `, if the function takes parameters.
- ` that produces a `type` using ` followed by the parameters, if the function does both.

The declaration is then followed by `:`, a newline and a [scope](#Scope) that ends with a [return](#Return).  
The name of the function can also be `Main` (see [Main](#Main) function).

### Instruction
Can be:
- A declaration of a [variable](#Variable-declaration).
- An [assignment](#Assignment).
- A function [call](#Function-call).
- An [operation](#List-operation) on a list.

### List operation
Syntax:
- `Insert `_X_` in ` _Y_, for inserting an element in a list.
- `Remove from `_Y_, for removing an element from a list.
- `Split `_Z_` in `_K_, for dividing the characters of a Text into a list of Texts.
- `Merge `Z` in `K, for merging a list of Texts into a single Text.

_X_ is a [direct](#Direct-value) value.  
_Y_ is [access](#Structure-access) to part of a structured variable.  
_Z_ and _K_ can be:
- The [name](#Name) of a variable.
- [Access](#Structure-access) to part of a structured variable.

### Literal
Syntax:
- Any whole, decimal or negative number for Number literals (e.g. `1`, `2.3` or `-4.5`).
- `True` or `False` for Boolean literals.
- Anything between a pair of `"` for Text literals (e.g. `"Hello, World!"`).
- `Nothing`.
- One or more literals between `[]` and separated by `, ` for Sequence literals (e.g. `[1, 2, 3]`).
- **Zero** or more literals between `||` and separated by `; ` for List literals (e.g. `|"a"; "b" ; "c"|`).
- One or more literals between `{}` and separated by `, ` for Kit literals (e.g. `{4, "d", False}`).

### Logical operation
Syntax: `(`_X_` `op` `_Y_`)`.

_X_ and _Y_ can be:
- A [direct](#Direct-value) value.
- Another logical operation.
- A [comparison](#Comparison-operation) operation.

_op_ can be:
- `and` for logical conjunction.
- `or` for logical disjunction.
- `not` for logical negation (only requires **one** operand, e.g. `(not X)`).

### Loop statement
Syntax: `Repeat `_X_`:`, followed by a newline and a [scope](#Scope).

_X_ can be:
- _Y_` times`, where _Y_ is a [direct](#Direct-value) value, for quantified iterations.
- `while `_K_, where _K_ is a [comparison](#Comparison-operation) or [logical](#Logical-operation) operation, for conditional iterations.

### Main
This function is the starting point for the program when it's executed.  
A file with no main function cannot be executed.

### Name
Syntax: One uppercase letter followed by zero or more uppercase letters, numbers or `_` (e.g. `ID_1`).

### Return
Syntax: `Return `_X_.

_X_ can be:
- A [direct](#Direct-value) or [special](#Special-value) value.
- An [arithmetical](#Arithmetical-operation), [logical](#Logical-operation) or [comparison](#Comparison-operation) operation.
- A [call](#Function-call) to a function, between `()`.

### Scope
A scope contains one or more:
- [Comments](#Comment).
- [Instructions](#Instruction), each terminated by `,`.
- [Conditional](#Conditional-statement) statements.
- [Loop](#Loop-statement) statements.

The last element of the scope is terminated by `;`.  
Only one `;` is needed if multiple scopes end on the same line.  
The last element cannot be a comment but can be a [return](#Return).  
If a scope is contained in another scope, all its elements are indented with **one** more tabulation than those of the other, starting from 1.  
E.g.
```
File
    Scope1
    Instruction,
        Scope2
            Scope3
            Instruction,
            Instruction;
    Instruction;
```

### Special value
Syntax:
- `Counter`, the iteration number of the current innermost [loop](#Loop-statement) [scope](#Scope)-wise.
- `size of `[name](#Name), the size of a specific structured variable.
- `Random`, a random whole number between 0 and 99.

### Structure access
Syntax: _X_` at position `_Y_.

_X_ is the [name](#Name) of a structured variable.

_Y_ can be:
- A positive, whole number.
- The [name](#Name) of a variable, to use its value.
- A [special](#Special-value) value.
- `@` and the [name](#Name) of a field, for kit variables only.

### Type
Syntax:
- `Number`, for numerical values.
- `Text`, for textual values.
- `Boolean`, for boolean values.
- `(Sequence of `_N_` `type`)`, for finite lists of homogeneous values.
- `(List of `type`)`, for infinite lists of homogeneous values.
- `(Kit of `_X_`)`, for finite lists of heterogeneous values.
- The [name](#Name) of a [declared](#Type-declaration) type.

_N_ is a whole, positive number.  
Sequence types with same type but different _N_ are considered distinct types.

_X_ is one or more type` `[name](#Name) separated by `, `, each one called a "field" of the kit.  
Kit types whose fields have different names but all corresponding types are considered equivalent types.

### Type declaration
Syntax: `Prepare a `[type](#Type)` with name `[name](#Name)`as a new type`.  
Names for types must always begin with `#`.

### Variable declaration
Syntax: `Prepare a `[type](#Type)` with name `[name](#Name).  
Can be followed by ` and value of ` and either a [direct](#Direct-value) or [special](#Special-value) value for initialization of the variable.

---
## Index
- [Arithmetical operation](#Arithmetical-operation)
- [Assignment](#Assignment)
- [Comment](#Comment)
- [Comparison operation](#Comparison-operation)
- [Conditional statement](#Conditional-statement)
- [Direct value](#Direct-value)
- [File](#File)
- [Function call](#Function-call)
- [Function declaration](#Function-declaration)
- [Instruction](#Instruction)
- [List operation](#List-operation)
- [Literal](#Literal)
- [Logical operation](#Logical-operation)
- [Loop statement](#Loop-statement)
- [Main](#Main)
- [Name](#Name)
- [Return](#Return)
- [Scope](#Scope)
- [Special value](#Special-value)
- [Structure access](#Structure-access)
- [Type](#Type)
- [Type declaration](#Type-declaration)
- [Variable declaration](#Variable-declaration)

