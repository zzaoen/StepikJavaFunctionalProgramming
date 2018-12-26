### Stepik Java Functional Programming
https://stepik.org/course/1595

### 目录
#### 基础题 Basic.java
2.2 Write a lambda expression that accepts two integers arguments and returns max of them. 

 
 
### 
2.11  
function composition
andThen和compose方法的调用的顺序
```$java
IntUnaryOperator mult2 = num -> num * 2;
IntUnaryOperator add3 = num -> num + 3;
IntUnaryOperator combinedOperator = add3.compose(mult2.andThen(add3)).andThen(mult2);
```
andThen是在之后计算，
compose是直接计算
mult2.andThen(add3) 先mult2，然后andThen 5 * 2 + 3 = 13 相当于add3(mult2)
add3.compose(mult2.andThen(add3)) 13 + 3 = 16
x.addThen(mul2) 16 * 2 = 32

2.14 
chain of responsibility pattern

2.15
stream operations
intermediate:  always lazy and return a new stream
e.g.: limit, distinct, sorted, filter, map, skip

terminal: not lazy and return a value or produce a side-effect
e.g.: average, max, reduce, anyMatch, collect, count

2.22
开始有flatmap

2.25
Existing collectors in Java8
Collectors.toCollection
Collectors.toList
Collectors.toCouncurrentMap
Collectors.toMap
Collectors.toSet

Collectors.partitioningBy
Collectors.groupingBy
Collectors.reducing
Collectors.summingDouble
Collectors.maxBy


2.36
monad:
It has a function unit(or pure): takes a value and return a monad wrapping the value
It has a function bind(or flatMap): takes a function that (the function) accepts a value and returning a monad
Monad is a structure that puts a value in a computational context


In practice we'll often use an additional method **map** that may be defined as combination of bind and unit. 
map: takes a function that (the function) accepts a value and returns a value.