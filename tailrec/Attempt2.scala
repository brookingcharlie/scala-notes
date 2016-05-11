import annotation.tailrec

object Attempt2 {
  def factorial(n: BigInt): BigInt = {
    @tailrec
    def factorialRecurse(acc: BigInt, n: BigInt): BigInt =
      if (n == 0) acc else factorialRecurse(acc * n, n - 1)
    factorialRecurse(1, n)
  }
  def main(args: Array[String]) {
    println(factorial(10000))
  }
}
