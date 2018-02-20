#! /bin/sh
for f in  CP3*
do  
    echo $f
#    mv $f/src/main/scala/* $f/src/test/scala
    cp testsUtils.scala $f/src/test/scala/fr/istic/si2/test/checkpoint3/testsUtils.scala
#    zip -ur $f CP3
done
