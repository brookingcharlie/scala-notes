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

println("# Evaluate ifStrict(true)")
println("Result: " + ifStrict(true)(onTrue, onFalse))

println("# Evaluate ifStrict(false)")
println("Result: " + ifStrict(false)(onTrue, onFalse))

println("# Evaluate ifLazy(true)")
println("Result: " + ifLazy(true)(onTrue, onFalse))

println("# Evaluate ifLazy(false)")
println("Result: " + ifLazy(false)(onTrue, onFalse))
