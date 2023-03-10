A simple Java reference implementation of the Unification algorithm, inspired by [1].

This implementation supports an elementary language for type expressions.
Elements of type expressions are:

- single-letter symbols are variables, otherwise use `V(MyVar)` for longer names
- literal symbols, like Nat, Int, Bool, use the syntax `L(Int)`, `L(Nat)`, `L(Bool)`
- the function type is `element -> element`

Equations have the form `TypeExpression = TypeExpression`, separated by a comma (`,`).

# Usage

You can use the class `UnificationTester.java` to write equations and run the unification algorithm.
To run it, you can execute the file `compile_and_run.sh` found in the root of the repository.

# Requirements

JDK 17

# References
[1] Pierce, Benjamin C. Types and programming languages. MIT press, 2002.
