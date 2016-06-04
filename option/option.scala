// Define a function to compute the mean of a sequnce of doubles,
// using Option rather than an exception to deal with an invalid argument.
// This demonstrates how errors can be handled in a type-safe way without exceptions.

val mean: Seq[Double] => Option[Double] = {
  case Nil => None
  case xs => Some(xs.sum / xs.length)
}
assert(mean(Nil) == None)
assert(mean(List(1, 2, 3)) == Some(2))

// map applies a function to the value wrapped in Some,
// returning the result of the function wrapped in a new Some.
// If the input is None, map returns None.

val f: Int => Int = _ * 2
assert(Some(1).map(f) == Some(2))
assert(None.map(f) == None)

// flatMap applies a function to the value wrapped in Some,
// where the function itself can return either Some or None.
// If the input in None, flatMap returns None.

val g: Int => Option[Int] = x => if (x > 0) Some(x * 2) else None
assert(Some(1).flatMap(g) == Some(2))
assert(Some(-1).flatMap(g) == None)
assert(None.flatMap(g) == None)

// getOrElse extracts the value from Some
// or returns the given default value for None.

assert(Some(1).getOrElse(0) == 1)
assert(None.getOrElse(0) == 0)

// orElse returns the same Option for Some
// or returns the given default Option for None.

assert(Some(1).orElse(Some(0)) == Some(1))
assert(None.orElse(Some(0)) == Some(0))

// filter applies a predicate to the value wrapped in Some,
// returning the original Some instance if the predicate is true
// or None is the predicate returns false.
// If the input is None, map returns None.

val h: Int => Boolean = x => x > 0
assert(Some(1).filter(h) == Some(1))
assert(Some(-1).filter(h) == None)
assert(None.filter(h) == None)

// Implement our own versions of Option functions

def map[X, Y](xs: Option[X])(f: X => Y): Option[Y] = xs match {
  case Some(x) => Some(f(x))
  case None => None
}
assert(map(Some(1))(f) == Some(2))
assert(map(None)(f) == None)

def getOrElse[X, Z >: X](xs: Option[X])(z: => Z): Z = xs match {
  case Some(x) => x
  case None => z
}
assert(getOrElse(Some(1))(0) == 1)
assert(getOrElse(None)(0) == 0)

def flatMap[X, Y](xs: Option[X])(f: X => Option[Y]): Option[Y] =
  getOrElse(map(xs)(f))(None)
assert(flatMap(Some(1))(g) == Some(2))
assert(flatMap(Some(-1))(g) == None)
assert(flatMap(None)(g) == None)

def orElse[X, Z >: X](xs: Option[X])(zs: => Option[Z]): Option[Z] =
  getOrElse(map(xs)(x => Some(x): Option[Z]))(zs)
assert(orElse(Some(1))(Some(0)) == Some(1))
assert(orElse(None)(Some(0)) == Some(0))

def filter[X](xs: Option[X])(p: X => Boolean): Option[X] =
  flatMap(xs)(x => if (p(x)) Some(x) else None)
assert(filter(Some(1))(h) == Some(1))
assert(filter(Some(-1))(h) == None)
assert(filter(None)(h) == None)

println("All tests passed!")
