# Scala lazy evaluation demo

## Example functions

```scala
// Define an "if" function taking strictly-evaluated arguments.
// Both arguments will be evaluated before being passed to the function,
// regardless of whether their value is actually needed.

def ifStrict[A](p: Boolean)(onTrue: A, onFalse: A): A = if (p) onTrue else onFalse

// Define an "if" function taking lazily-evaluated arguments.
// The expression supplied for each argument will only be evaluated if needed:
// based on the supplied boolean, we'll only evalute either onTrue or onFalse.

def ifLazy[A](p: Boolean)(onTrue: => A, onFalse: => A): A = if (p) onTrue else onFalse

// Define trivial functions to use for onTrue and onFalse arguments.
// These have the side-effect of printing to stdout when evaluated,
// allowing us to see when each argument's expression is evaluted.

def onTrue(): String = {println("Evaluate onTrue"); "True!"}
def onFalse(): String = {println("Evaluate onFalse"); "False!"}
```

## Evaluation

```scala
scala> ifStrict(true)(onTrue, onFalse)
Evaluate onTrue
Evaluate onFalse
Result: True!
```

```scala
scala> ifStrict(false)(onTrue, onFalse)
Evaluate onTrue
Evaluate onFalse
Result: False!
```

```scala
scala> ifLazy(true)(onTrue, onFalse)
Evaluate onTrue
Result: True!
```

```scala
scala> ifLazy(false)(onTrue, onFalse)
Evaluate onFalse
Result: False!
```
