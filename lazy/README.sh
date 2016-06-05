#!/bin/bash

show_expr() {
  echo '```'
  echo 'scala>' $1
  scala -i 'lazy.scala' -e "println(\"Result: \" + $1)"
  echo '```'
}

echo '# Scala lazy evaluation demo'
echo
echo '## Example functions'
echo
echo '```'
cat lazy.scala
echo '```'
echo
echo '# Evaluation'
echo
show_expr 'ifStrict(true)(onTrue, onFalse)'
echo
show_expr 'ifStrict(false)(onTrue, onFalse)'
echo
show_expr 'ifLazy(true)(onTrue, onFalse)'
echo
show_expr 'ifLazy(false)(onTrue, onFalse)'
