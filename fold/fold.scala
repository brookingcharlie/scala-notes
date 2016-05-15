import annotation._

// Implement our own fold functions

def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = as match {
  case (h :: t) => f(h, foldRight(t, z)(f))
  case Nil => z
}
assert(foldRight(List(1, 2, 3), 0)((x, a) => a - x) == -6)

@tailrec
def foldLeft[A, B](as: List[A], z: B)(f: (B, A) => B): B = as match {
  case (h :: t) => foldLeft(t, f(z, h))(f)
  case Nil => z
}
assert(foldLeft(List(1, 2, 3), 0)((a, x) => a - x) == -6)

// Implement various list functions in terms of fold

def sum(as: List[Int]): Int = foldLeft(as, 0)((a, x) => a + x)
assert(sum(List(1, 2, 3)) == 6)

def product(as: List[Int]): Int = foldLeft(as, 0)((a, x) => a * x)
assert(product(List(1, 2, 3)) == 0)

def length[A](as: List[A]): Int = foldLeft(as, 0)((a, x) => a + 1)
assert(length(List(1, 2, 3)) == 3)

def id[A](as: List[A]): List[A] = foldRight(as, List[A]())((x, a) => x :: a)
assert(id(List(1, 2, 3)) == List(1, 2, 3))

def reverse[A](as: List[A]): List[A] = foldLeft(as, List[A]())((a, x) => x :: a)
assert(reverse(List(1, 2, 3)) == List(3, 2, 1))

def append[A](as: List[A], bs: List[A]): List[A] = as.foldRight(bs)((x, a) => x :: a)
assert(append(List(1, 2), List(3, 4, 5)) == List(1, 2, 3, 4, 5))

def concat[A](xss: List[List[A]]): List[A] = xss.foldRight(List[A]())(append)
assert(concat(List(List(1, 2), List(), List(3, 4, 5))) == List(1, 2, 3, 4, 5))

// Show that foldRight can be written using foldLeft, avoiding a stack overflow

def foldRightUsingFoldLeft[A, B](as: List[A], z: B)(f: (A, B) => B): B =
  foldLeft(reverse(as), z)((a, x) => f(x, a))
assert(foldRightUsingFoldLeft(List(1, 2, 3), 0)((x, a) => a - x) == -6)

// In reality, we should use Scala's standard fold functions on List

assert(List(1, 2, 3).foldLeft(0)((a, x) => a - x) == -6)
assert(List(1, 2, 3).foldRight(0)((x, a) => a - x) == -6)

// Scala provides an operator notation for foldLeft (/:) and foldRight (:\).
// The operators are intended to look like the expression tree (e.g. /: grows to the left)

assert((0 /: List(1, 2, 3))((a, x) => a - x) == List(1, 2, 3).foldLeft(0)((a, x) => a - x))
assert((List(1, 2, 3) :\ 0)((x, a) => a - x) == List(1, 2, 3).foldRight(0)((x, a) => a - x))

println("All tests passed!")
