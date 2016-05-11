object Attempt1 {
  def factorial(n: BigInt): BigInt = {
    if (n == 0) 1 else n * factorial(n - 1)
  }
  def main(args: Array[String]) {
    println(factorial(10000))
  }
}
