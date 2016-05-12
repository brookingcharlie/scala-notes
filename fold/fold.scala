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
def product(as: List[Int]): Int = foldLeft(as, 0)((a, x) => a * x)
def length[A](as: List[A]): Int = foldLeft(as, 0)((a, x) => a + 1)
assert(sum(List(1, 2, 3)) == 6)
assert(product(List(1, 2, 3)) == 0)
assert(length(List(1, 2, 3)) == 3)

def id[A](as: List[A]): List[A] = foldRight(as, List[A]())((x, a) => x :: a)
def reverse[A](as: List[A]): List[A] = foldLeft(as, List[A]())((a, x) => x :: a)
assert(id(List(1, 2, 3)) == List(1, 2, 3))
assert(reverse(List(1, 2, 3)) == List(3, 2, 1))

def append[A](as: List[A], bs: List[A]): List[A] = (as :\ bs)((x, a) => x :: a)
assert(append(List(1, 2), List(3, 4, 5)) == List(1, 2, 3, 4, 5))

def concat[A](xss: List[List[A]]): List[A] = (xss :\ List[A]())(append)
assert(concat(List(List(1, 2), List(), List(3, 4, 5))) == List(1, 2, 3, 4, 5))

// Show that foldRight can be written using foldLeft, making it tail-recursive

def foldRightUsingFoldLeft[A, B](as: List[A], z: B)(f: (A, B) => B): B =
  foldLeft(reverse(as), z)((a, x) => f(x, a))
assert(foldRightUsingFoldLeft(List(1, 2, 3), 0)((x, a) => a - x) == -6)

// In reality, we should use Scala's standard fold functions on List

assert(foldLeft(List(1, 2, 3), 0)((a, x) => a - x) == List(1, 2, 3).foldLeft(0)((a, x) => a - x))
assert(foldRight(List(1, 2, 3), 0)((x, a) => a - x) == List(1, 2, 3).foldRight(0)((x, a) => a - x))

// Scala also provides an operator notation, where /: or :\ matching the balance of each expression tree

assert((0 /: List(1, 2, 3))((a, x) => a - x) == List(1, 2, 3).foldLeft(0)((a, x) => a - x))
assert((List(1, 2, 3) :\ 0)((x, a) => a - x) == List(1, 2, 3).foldRight(0)((x, a) => a - x))

println("All tests passed!")
