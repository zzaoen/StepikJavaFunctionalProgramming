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