# Scala tail call optimisation demo

## First attempt

```
$ cat Attempt1.scala
```

```scala
object Attempt1 {
  def factorial(n: BigInt): BigInt = {
    if (n == 0) 1 else n * factorial(n - 1)
  }
  def main(args: Array[String]) {
    println(factorial(10000))
  }
}
```

```
$ scala Attempt1.scala 2>&1 | fold | head
```

```
java.lang.StackOverflowError
	at scala.math.BigInt$.apply(BigInt.scala:39)
	at scala.math.BigInt$.int2bigInt(BigInt.scala:97)
	at scala.math.BigInt.isValidInt(BigInt.scala:130)
	at scala.math.ScalaNumericAnyConversions$class.unifiedPrimitiveEquals(Sc
alaNumericConversions.scala:113)
	at scala.math.BigInt.unifiedPrimitiveEquals(BigInt.scala:112)
	at scala.math.BigInt.equals(BigInt.scala:125)
	at scala.runtime.BoxesRunTime.equalsNumNum(BoxesRunTime.java:168)
	at scala.runtime.BoxesRunTime.equalsNumObject(BoxesRunTime.java:140)
```

## Checking at compile time

```
$ diff -u Attempt1{,Annotated}.scala
```

```diff
--- Attempt1.scala	2016-05-11 20:42:17.000000000 +1000
+++ Attempt1Annotated.scala	2016-05-11 21:01:17.000000000 +1000
@@ -1,4 +1,5 @@
 object Attempt1 {
+  @annotation.tailrec
   def factorial(n: BigInt): BigInt = {
     if (n == 0) 1 else n * factorial(n - 1)
   }
```

```
$ scala Attempt1Annotated.scala 2>&1 | fold | head
```

```
/Users/cbrookin/Documents/code/scala-notes/tailrec/Attempt1Annotated.scala:4: er
ror: could not optimize @tailrec annotated method factorial: it contains a recur
sive call not in tail position
    if (n == 0) 1 else n * factorial(n - 1)
                         ^
one error found
```

## Second attempt

```
$ cat Attempt2.scala
```

```scala
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
```

```
$ scala Attempt2.scala 2>&1 | fold | head
```

```
28462596809170545189064132121198688901480514017027992307941799942744113400037644
43772990786757784775815884062142317528830042339940153518739052421161382716174819
82419982759241828925978789812425312059465996259867065601615720360323979263287367
17055741975962099479720346153698119897092611277500484198845410475544642442136573
30307670362882580354896746111709736957860367019107151273058728104115864056128116
53853259684258259955846881464304255898366493170592517172042765974074461334000541
94052462303436869154059404066227828248371512038322178644627183822923899638992827
22187970245938769380309462733229257055545969002787528224254434802112755901916942
54290289169072190970836905398737474524833728995218023632827412170402680867692104
51555840567172555372015852132829034279989818449313610640381489304499621599999359
```

## What if we disable optimisation?

```
$ scala -g:notailcalls Attempt2.scala 2>&1 | fold | head
```

```
java.lang.StackOverflowError
	at scala.math.BigInt$.long2bigInt(BigInt.scala:101)
	at scala.math.BigInt.isValidLong(BigInt.scala:131)
	at scala.math.BigInt.equals(BigInt.scala:125)
	at scala.runtime.BoxesRunTime.equalsNumNum(BoxesRunTime.java:168)
	at scala.runtime.BoxesRunTime.equalsNumObject(BoxesRunTime.java:140)
	at Main$.factorialRecurse$1(Attempt2.scala:7)
	at Main$.factorialRecurse$1(Attempt2.scala:7)
	at Main$.factorialRecurse$1(Attempt2.scala:7)
	at Main$.factorialRecurse$1(Attempt2.scala:7)
```
