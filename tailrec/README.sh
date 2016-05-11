#!/bin/bash

show_cmd() {
  echo '```'
  echo $ $1
  echo '```'
  echo
  echo '```'$2
  bash -c "$1"
  echo '```'
}

echo '# Scala tail call optimisation demo'
echo
echo '## First attempt'
echo
show_cmd 'cat Attempt1.scala' scala
echo
show_cmd 'scala Attempt1.scala 2>&1 | fold | head'
echo
echo '## Checking at compile time'
echo
show_cmd 'diff -u Attempt1{,Annotated}.scala' diff
echo
show_cmd 'scala Attempt1Annotated.scala 2>&1 | fold | head'
echo
echo '## Second attempt'
echo
show_cmd 'cat Attempt2.scala' scala
echo
show_cmd 'scala Attempt2.scala 2>&1 | fold | head'
echo
echo '## What if we disable optimisation?'
echo
show_cmd 'scala -g:notailcalls Attempt2.scala 2>&1 | fold | head'
