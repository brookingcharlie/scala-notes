// We can also use Either to cope with errors. Its essential defintion is:
//
//   sealed trait Either[+E, +A]
//   case class Left[+E](value: E) extends Either[E, Nothing]
//   case class Right[+A](value: A) extends Either[Nothing, A]
//
// This represents a disjoint union of two types. By convention, we use Left to
// capture error values (esp. Exceptions) and Right for correct (i.e. "right") values.
//
// This differs from Option which, in the case of None, gives no information
// about the actual error that occurred.

def f(x: Int): Either[String, Int] = if (x < 0) Left("Negative!") else Right(x)
assert(f(-1) == Left("Negative!"))
assert(f(0) == Right(0))

def div(x: Int, y: Int): Either[Exception, Int] =
  try Right(x / y)
  catch {case e: Exception => Left(e)}
assert(div(2, 1) == Right(2))
assert(div(2, 0) match {
  case Left(e) => e.isInstanceOf[java.lang.ArithmeticException]
  case _ => false
})
