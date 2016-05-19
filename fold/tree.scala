// Define a recursive tree data structure

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

// Implement various tree functions

def size[A](tree: Tree[A]): Int = tree match {
  case Leaf(value) => 1
  case Branch(left, right) => 1 + size(left) + size(right)
}
assert(size(Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))) == 5)

def maximum(tree: Tree[Int]): Int = tree match {
  case Leaf(value) => value
  case Branch(left, right) => maximum(left) max maximum(right)
}
assert(maximum(Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))) == 3)

def depth[A](tree: Tree[A]): Int = tree match {
  case Leaf(value) => 0
  case Branch(left, right) => 1 + (depth(left) max depth(right))
}
assert(depth(Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))) == 2)

def map[A, B](tree: Tree[A])(f: A => B): Tree[B] = tree match {
  case Leaf(value) => Leaf(f(value))
  case Branch(left, right) => Branch(map(left)(f), map(right)(f))
}
assert(
  map(Branch(Leaf(1), Branch(Leaf(2), Leaf(3))))(_ + 1) ==
  Branch(Leaf(2), Branch(Leaf(3), Leaf(4)))
)

// Show that these can be generalised using a tree fold function

def fold[A, B](tree: Tree[A])(lf: A => B)(bf: (B, B) => B): B = tree match {
  case Leaf(value) => lf(value)
  case Branch(left, right) => bf(fold(left)(lf)(bf), fold(right)(lf)(bf))
}

def sizeUsingFold[A](tree: Tree[A]): Int =
  fold(tree)(_ => 1)(1 + _ + _)
assert(sizeUsingFold(Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))) == 5)

def maximumUsingFold(tree: Tree[Int]): Int =
  fold(tree)(a => a)(_ max _)
assert(maximumUsingFold(Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))) == 3)

def depthUsingFold[A](tree: Tree[A]): Int =
  fold(tree)(_ => 0)((lb, rb) => 1 + (lb max rb))
assert(depthUsingFold(Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))) == 2)

def mapUsingFold[A, B](tree: Tree[A])(f: A => B): Tree[B] =
  fold(tree)(a => Leaf(f(a)): Tree[B])(Branch(_, _))
assert(
  mapUsingFold(Branch(Leaf(1), Branch(Leaf(2), Leaf(3))))(_ + 1) ==
  Branch(Leaf(2), Branch(Leaf(3), Leaf(4)))
)

println("All tests passed!")
