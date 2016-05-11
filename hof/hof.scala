// Our implementations of higher-order functions

def partial1[A,B,C](a: A, f: (A, B) => C): B => C =
  (b: B) => f(a, b)

def curry[A,B,C](f: (A, B) => C): A => (B => C) =
  (a: A) => (b: B) => f(a, b)

def uncurry[A,B,C](f: A => B => C): (A, B) => C =
  (a: A, b: B) => f(a)(b)

def ourCompose[A,B,C](f: B => C, g: A => B): A => C =
  (a: A) => f(g(a))

// Some simple tests

val multiply = (a: Int, b: Int) => (a * b)
val multiplyBy2 = partial1(2, multiply)
val curriedMultiply = curry(multiply)
assert(multiplyBy2(4) == 8)
assert(curriedMultiply(2)(4) == 8)

val precurriedMultiply = (a: Int) => (b: Int) => a * b
val uncurriedMultiply = uncurry(precurriedMultiply)
assert(uncurriedMultiply(2, 4) == 8)

val f = (b: Int) => b * 2
val g = (a: Int) => a + 1
assert(ourCompose(f, g)(3) == 8)

// Standard library functions

assert((f compose g)(3) == 8)
assert((g andThen f)(3) == 8)

println("All tests passed!")
