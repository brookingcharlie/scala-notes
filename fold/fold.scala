import annotation._

// Implement our own fold functions

def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = as match {
  case (h :: t) => f(h, foldRight(t, z)(f))
  case Nil => z
}
assert(foldRight(List(1, 2, 3), 0)((a, z) => z - a) == -6)

@tailrec
def foldLeft[A, B](as: List[A], z: B)(f: (B, A) => B): B = as match {
  case (h :: t) => foldLeft(t, f(z, h))(f)
  case Nil => z
}
assert(foldLeft(List(1, 2, 3), 0)((z, a) => z - a) == -6)

// Implement various list functions in terms of fold

def sum(as: List[Int]): Int = foldLeft(as, 0)((z, a) => z + a)
assert(sum(List(1, 2, 3)) == 6)

def product(as: List[Int]): Int = foldLeft(as, 0)((z, a) => z * a)
assert(product(List(1, 2, 3)) == 0)

def length[A](as: List[A]): Int = foldLeft(as, 0)((z, a) => z + 1)
assert(length(List(1, 2, 3)) == 3)

def id[A](as: List[A]): List[A] = foldRight(as, List[A]())((a, z) => a :: z)
assert(id(List(1, 2, 3)) == List(1, 2, 3))

def reverse[A](as: List[A]): List[A] = foldLeft(as, List[A]())((z, a) => a :: z)
assert(reverse(List(1, 2, 3)) == List(3, 2, 1))

def append[A](as: List[A], bs: List[A]): List[A] = foldRight(as, bs)((a, z) => a :: z)
assert(append(List(1, 2), List(3, 4, 5)) == List(1, 2, 3, 4, 5))

def concat[A](xss: List[List[A]]): List[A] = foldRight(xss, List[A]())(append)
assert(concat(List(List(1, 2), List(), List(3, 4, 5))) == List(1, 2, 3, 4, 5))

def map[A, B](as: List[A])(f: A => B): List[B] =
  foldRight(as, List[B]())((a, z) => f(a) :: z)
assert(map(List(1, 2, 3))(_ + 1) == List(2, 3, 4))

def filter[A](as: List[A])(f: A => Boolean): List[A] =
  foldRight(as, List[A]())((a, z) => if (f(a)) a :: z else z)
assert(filter(List(1, 2, 3, 4))(_ % 2 == 0) == List(2, 4))

// Show that foldRight can be written using foldLeft, avoiding a stack overflow

def foldRightUsingFoldLeft[A, B](as: List[A], z: B)(f: (A, B) => B): B =
  foldLeft(reverse(as), z)((z, a) => f(a, z))
assert(foldRightUsingFoldLeft(List(1, 2, 3), 0)((a, z) => z - a) == -6)

// In reality, we should use Scala's standard fold functions on List

assert(List(1, 2, 3).foldLeft(0)((z, a) => z - a) == -6)
assert(List(1, 2, 3).foldRight(0)((a, z) => z - a) == -6)

// Scala provides an operator notation for foldLeft (/:) and foldRight (:\).
// The operators are intended to look like the expression tree (e.g. /: grows to the left)

assert((0 /: List(1, 2, 3))((z, a) => z - a) == List(1, 2, 3).foldLeft(0)((z, a) => z - a))
assert((List(1, 2, 3) :\ 0)((a, z) => z - a) == List(1, 2, 3).foldRight(0)((a, z) => z - a))

println("All tests passed!")
