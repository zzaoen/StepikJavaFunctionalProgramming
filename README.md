## Stepik Java Functional Programming
https://stepik.org/course/1595



### 基础题

**Basic.java**

2.2.1 Write a lambda expression that accepts two integers arguments and returns max of them. 

```java
# 1
IntBinaryOperator operator = (x, y) -> x > y ? x : y;

# 2
IntBinaryOperator operator = Math::max;
```

2.2.2 Write a lambda expression that accepts a long value and returns a next even number.

```java
# 1
IntUnaryOperator operator = x -> x + 2 - (x % 2);

# 2
IntUnaryOperator operator = x -> (x | 1) + 1;
```



2.3 Write a lambda expression that accepts seven (!) string arguments and returns a string in upper case concatenated from all of them (in the order of arguments).

 ```java
# 1
(a, b, c, d, e, f, g) -> (a + b + c + d + e + f + g).toUpperCase();

# 2
(a, b, c, d, e, f, g) -> Stream.of(a, b, c, d, e, f, g)
    .reduce("", String::concat)
    .toUpperCase();
 ```

在Java中没有现成可以接受7个字符串并返回1个字符串的方法，不过我们可以手动写一个函数式接口满足这个条件。

```java
@FunctionalInterface
interface SevenArgsFunction<A, B, C, D, E, F, G, R>{
    R apply(A a, B b, C c, D d, E e, F f, G g);
}
//调用
SevenArgsFunction<String, String, String, String, String, String, String, String> function = (a, b, c, d, e, f, g) -> (a + b + c + d + e + f + g).toUpperCase();
function.apply("a", "b", "c", "d", "e", "f", "g");
```

2.4 Write a lambda expression that accepts two long arguments as a range borders and calculates (returns) production of all numbers in this range (inclusively).

```java
# 1
LongBinaryOperator operator = (x, y) -> {
    int res = 1;
    while (x < y)
        res *= y--;
    return res;
};

# 2
LongBinaryOperator operator = (x, y) -> LongStream.rangeClosed(x, y)
    .reduce(1L, (acc, temp) -> acc * temp);

# 3
LongBinaryOperator operator = (x, y) -> LongStream.rangeClosed(x, y)
    .reduce(1L, Math::multiplyExact);
```



2.5 Write a lambda expression that accepts a list of strings and returns new list of distinct strings (without repeating). The order of elements in the result list may be any (elements will be sorted by the testing system).

```java
# 1
Function<List<String>, List<String>> listConsumer = strings -> strings.stream()
    .distinct()
    .collect(Collectors.toList());

# 2
Function<List<String>, List<String>> listFunction = strings -> new ArrayList(new HashSet(strings));
```



2.6.1 Using closure write a lambda expression that calculates a∗x2+b∗x+c where a, b, c are context final variables. Note, the result is double

```java
//final int a = 1, b = 2, c = 3;
DoubleUnaryOperator operator = x -> a * x * x + b * x + c;
```



**Note**

Operator和Function功能类似，都是接受输入并返回结果。

IntUnaryOperator和IntBinaryOperator分别接受1个和2个int类型的输入，执行后返回一个int类型的结果。

Function<Integer, Integer>和BinaryFunction<Integer, Integer, Interger>分别接受1个和2个Integer类型的输入，执行后返回一个Integer类型的结果。也有和类型绑定的Function，比如IntFunction<R>, LongFunction<R>, DoubleFunction<R>等，R指定返回的类型。



2.6.2 Using closure write a lambda expression that adds prefix (before) and suffix (after) to its single string argument; prefix and suffix are final variables and will be available in the context during testing.

```java
//final String prefix = "pre-", suffix = "-suf";
# 1
Function<String, String> stringFunction = s -> prefix + s.trim() + suffix;

# 2
Function<String, String> stringFunction = s -> String.join("", prefix, s.trim(), suffix);
```



2.9 Behaviour parametrization with lambda expressions. 1. get list with all non-empty accounts (balance > 0) and save it to the variable nonEmptyAccounts; 2. get all non-locked accounts with too much money (balance >= 100 000 000) and save it to the variable accountsWithTooMuchMoney

```java
//Account account1 = new Account("111", 100000001, false);
//Account account2 = new Account("222", 0, false);
//Account account3 = new Account("333", 1000, false);
//List<Account> accounts = Arrays.asList(account1, account2, account3);
# 1
List<Account> balanceOverZeroList = accounts.stream()
                .filter(account -> account.getBalance() > 0)
                .collect(Collectors.toList());
List<Account> list = accounts.stream()
                .filter(account -> (account.getBalance() >= 100000000 && !account.isLocked()))
                .collect(Collectors.toList());

# 2
Predicate<Account> balanceOverZeroPred = (Account account) -> account.getBalance() > 0;
Predicate<Account> balanceTooMuchPred = (Account account) -> account.getBalance() > 100000000;
Predicate<Account> isLockedPred = (Account account) -> !account.isLocked();

List<Account> balanceOverZeroList = accounts.stream()
                .filter(balanceOverZeroPred).collect(Collectors.toList());
List<Account> list = accounts.stream()
 			   .filter(balanceTooMuchPred.and(isLockedPred))
                .collect(Collectors.toList());
```



2.10 Write your own functional interface (TernaryIntPredicate) and use it with a lambda expression. The interface must have a single non-static (and non-default) method test with three int arguments that returns boolean value. The lambda expression has to return true if all passed values are different otherwise false.

```java
@FunctionalInterface
interface TernaryIntPredicate {
    boolean test(int arg1, int arg2, int arg3);
}
//调用
TernaryIntPredicate allValuesAreDifferentPredicate = (x, y, z) -> x != y && y != z && x != z;
```



2.11  Understanding of the function composition 

**compose**和**andThen**

```
default IntUnaryOperator compose(IntUnaryOperator before) {
	Objects.requireNonNull(before);
	return (int v) -> applyAsInt(before.applyAsInt(v));
}
default IntUnaryOperator andThen(IntUnaryOperator after) {
	Objects.requireNonNull(after);
	return (int t) -> after.applyAsInt(applyAsInt(t));
}
```

compose(before): Returns a composed operator that first applies the {@code before} operator to its input, and then applies this operator to the result.

a.compse(b)是b先执行并将结果作为参数带入到a中执行。

andThen(after): Returns a composed operator that first applies this operator to its input, and then applies the {@code after} operator to the result.

a.addThen(b)是a先执行并将结果作为参数带入到b中执行。

```java
IntUnaryOperator mult2 = num -> num * 2;
IntUnaryOperator add3 = num -> num + 3;
IntUnaryOperator combinedOperator = add3.compose(mult2.andThen(add3)).andThen(mult2);
```

mult2.andThen(add3) 先mult2，然后andThen 5 * 2 + 3 = 13
add3.compose(13) 13 + 3 = 16
16.addThen(mult2) 16 * 2 = 32



2.12 Write the disjunctAll method that accepts a list of IntPredicate's and returns a single IntPredicate. The result predicate is a **disjunction** of all input predicates.

```java
//List<IntPredicate> predicates = ...;
# 1
IntPredicate resPredicate = x -> false;
predicates.forEach(predicate -> resPredicate.or(predicate));

# 2
IntPredicate resPredicate = predicates.stream().reduce(x -> false, IntPredicate::or);

# 3
IntPredicate resPredicate = predicates.stream().reduce(IntPredicate::or).orElse(x -> false);
```



2.17 Write a method using Stream API to check the input number is prime or not.

```java
# 1
boolean res = !LongStream.range(2, number/2+1).anyMatch(x -> number % x == 0);
    
# 2
boolean res = LongStream.range(2, number / 2 + 1).noneMatch(i -> number % i == 0);

# 3
boolean res = new BigInteger(String.valueOf(number)).isProbablePrime(1)    
```

2.18 Create a stream that will detect bad words in a text according to a bad words list. All words in the text are divided by whitespaces (always only one whitespace between two words).

```java
//String text = ...;
//List<String> badWords = ...;
# 1
Stream<String> res = badWords.stream().filter(text::contains).sorted().distinct();

# 2
Stream<String> res = Arrays.stream(text.split("\\s")).filter(badWords::contains).sorted().distinct();

# 3
Stream<String> res = Arrays.stream(text.split("\\s")).filter(s -> badWords.stream().anyMatch(badWord -> badWord.equals(s))).sorted().distinct();
```

第一种方法是将List<String> badWords中的每一个元素通过String.contains()方法与text比较；

第二种方法是将text按照空格拆分，得到的每一个元素与通过List.contains()方法与badWords比较；

第三种方法和第二种类似。



2.19 You have two IntStream. The first stream contains even numbers and the second stream contains odd numbers. Create the third stream that contains numbers from both streams which is divisible by 3 and 5. Two first suitable numbers in the sorted list must be skipped.  

```java
//IntStream evenStream = ...;
//IntStream oddStream = ...;
# 1
IntStream res = IntStream.concat(evenStream, oddStream)
                .filter(x -> x % 15 == 0)
                .sorted()
                .skip(2);

# 2
List<Integer> resList = evenStream.filter(x -> x % 15 == 0)
                .collect(LinkedList::new, List::add, List::addAll);
resList.addAll(oddStream.filter(x -> x % 15 == 0)
                .collect(LinkedList::new, List::add, List::addAll));
IntStream res = resList.stream().mapToInt(x -> x).sorted().skip(2);
```

第二个方法中，collect()方法不能直接通过collect(Collectors.toList())将结果转成List的原因是evenStream是IntStream，IntStream的collect()方法定义如下：

```java
<R> R collect(Supplier<R> supplier,
                  ObjIntConsumer<R> accumulator,
                  BiConsumer<R, R> combiner);
```

而Stream的collect()方法定义如下：

```java
<R, A> R collect(Collector<? super T, A, R> collector);
```

如果不是IntStream，而是Stream，那么调用collect(Collectors.toList())就不会有问题了。

```java
List<Integer> list = Arrays.asList(1, 2, 3);
        List<Integer> collect = list.stream().filter(x -> x > 2).collect(Collectors.toList());
```



2.20 Write a method to calculate a factorial value using stream.

```java
//long n = 5;
# 1
long res = LongStream.rangeClosed(2, n).reduce(1L, Math::multiplyExact);

# 2
long res = LongStream.rangeClosed(2, n).reduce(1L, (x, y) -> x * y);    
```



2.21 Write a method for calculating the sum of odd numbers in the given interval (inclusively) using Stream API.

```java
# 1
long res = LongStream.rangeClosed(start, end)
                .filter(x -> x % 2 == 1)
                .sum();

# 2
long res = LongStream.rangeClosed(start, end)
                .filter(x -> x % 2 == 1)
                .reduce(0L, Long::sum);
```



2.31 Create a parallel LongStream for filtering prime numbers in the given range (inclusively).

```java
LongStream res = LongStream.rangeClosed(rangeBegin, rangeEnd)
                .parallel()
                .filter(x -> (x & 1) != 0);
```







### chain of responsibility 

**./chainofresp/TestCOR.java**

2.14  The chain of responsibility pattern in the functional style



### stream operations 

2.15 knowledge of stream operations
**intermediate**:  always lazy and return a new stream
e.g.: limit, distinct, sorted, filter, map, skip

**terminal**: not lazy and return a value or produce a side-effect
e.g.: average, max, reduce, anyMatch, collect, count



### flatmap

**TestFlatMap.java**

2.23 Calculates the number of employees with salary >= threshold (only for 111- departments)

```java
//List<Department> departments = ...;
//long threshold = ...;

# 1
long res = departments.stream()
                .filter(department -> department.getCode().startsWith("111-"))
                .flatMap(department -> department.getEmployees().stream())
                .filter(employee -> employee.getSalary() >= threshold)
                .count();

# 2
long res = departments.stream()
                .filter(department -> department.getCode().startsWith("111-"))
                .flatMapToLong(department -> department.getEmployees().stream().mapToLong(Employee::getSalary))
                .filter(salary -> salary >= threshold)
                .count();

# 3
long res = departments.stream()
                .filter(department -> department.getCode().startsWith("111-"))
                .map(Department::getEmployees)
                .flatMap(list -> list.stream()) //.flatMap(List::stream)
                .map(Employee::getSalary)
                .filter(salary -> salary >= threshold)
                .count();
```

在第二种方法中有一句调用

```java
flatMapToLong(department -> department.getEmployees().stream().mapToLong(Employee::getSalary))
```

flatMapToLong和mapToLong，这两个方法都定义在Stream类中

```java
LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
LongStream mapToLong(ToLongFunction<? super T> mapper);
```

从作用上来理解，mapToLong()方法得到对象可以直接得到long类型的属性，比如mapToLong(Employee::getSalary)，getSalary返回的是long类型的值。而flatMapToLong()方法作用的对象内部包含一个集合，从集合包含的对象可以得到long类型的属性，比如通过department对象获得一个Employee的List，通过Employee对象获得long类型的salary。



2.24 Write a method using Stream API that calculates the total sum of canceled transactions for all non-empty accounts (balance > 0).

```java
# 1
long res = accounts.stream()
                .filter(account -> account.getBalance() > 0)
                .flatMap(account -> account.getTransactions().stream())
                .filter(transaction -> transaction.getState().equals("CANCELED"))
                .mapToLong(Transaction::getSum)
                .sum(); //.reduce(0L, Long::sum);    

# 2
long res = accounts.stream()
                .filter(account -> account.getBalance() > 0)
                .flatMapToLong(accout -> accout.getTransactions()
                        .stream()
                        .filter(transaction -> transaction
                                .getState().equals("CANCELED"))
                        .mapToLong(Transaction::getSum))
                .sum()
```





### Collectors

**TestCollectors.java**

2.25 Existing collectors in Java8

```java
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
```



2.26 Write a collector that evaluates the product of squares of integer numbers in a Stream<Integer>.

```java
//List<Integer> numbers = Arrays.asList(1, 2, 3);
# 1
long res = numbers.stream().collect(reducing(1, (acc, x) -> acc * x * x));

# 2
long res = numbers.stream().collect(reducing(1, x -> x * x, Math::multiplyExact));
```



2.27 Write a collector that partitions all words in a stream into two groups: palindromes (true) and usual words (false).

```java
//String[] words = new String[]{"aaa", "aa", "a", "ba"};
# 1
Map<Boolean, List<String>> res = Arrays.stream(words)
                .collect(Collectors.partitioningBy(word -> new StringBuilder(word).reverse().equals(word)));

# 2
Map<Boolean, List<String>> res = Arrays.stream(words)
                .collect(Collectors.partitioningBy(word -> word.contentEquals(new StringBuffer(word).reverse())));    
```



2.28 Write a collector that calculates the total sum of transactions (long type, not integer) by each account (i.e. by account number).

```java
//Transaction transaction1 = new Transaction("123", 100, new Account("111", 1000));
//Transaction transaction2 = new Transaction("f22", 1000, new Account("112", 1000));
//List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
# 1
Map<String, Long> res = transactions.stream()
                .collect(Collectors.groupingBy(x -> x.getAccount().getNumber(), Collectors.summingLong(Transaction::getSum)));

# 2
Map<String, Long> res = transactions.stream()
                .collect(Collectors.toMap(x -> x.getAccount().getNumber(), Transaction::getSum, Long::sum));
```



2.29 Write a collector that calculates how many times was clicked* each url by users.

```java
LogEntry logEntry1 = new LogEntry("2017", "zz", "www.google.com");
LogEntry logEntry2 = new LogEntry("2018", "ab", "www.baidu.com");
LogEntry logEntry3 = new LogEntry("2018", "ab", "www.google.com");
List<LogEntry> logs = Arrays.asList(logEntry1, logEntry2, logEntry3);
Map<String, Long> res = logs.stream()
                .collect(Collectors.groupingBy(LogEntry::getUrl, Collectors.counting()));
```



### Curry

**TestCurry.java**

2.33.1 Write a curried form of the function f(x,y,z)=x+y∗y+z∗z∗z using lambda expressions in Java 8 style.

```java
Function<Integer, Function<Integer, Function<Integer, Integer>>> function = x -> y -> z -> x + y * y + z * z * z;
function.apply(2).apply(3).apply(4);
```

接收3个Integer类型的值，并返回1个Integer类型的值。

```java
Function<Integer, Function<Integer, Function<Integer, Integer>>> function = ...;
```

第一层可以看成是Function<Integer,  Function>，所以第一层调用得到的是结果还是一个Function类型的对象。

```java
Function<Integer, Function<Integer, Integer>> = function.apply(2);
```

最后一层Function的第二个参数指定了返回值的类型。



2.33.2 Write a curried function (using lambdas) that accepts four string arguments and concatenated all in one string.

```java
Function<String, Function<String, Function<String, Function<String, String>>>> function = str1 -> str2 -> str3 -> str4 -> str1.toLowerCase() + str3.toUpperCase() + str2.toLowerCase() + str4.toUpperCase();
function.apply("aa").apply("bb").apply("cc").apply("dd");
```



2.34 

**multifunctionalMapper** accepts a list of operators (mappers) and returns a new operator. The returned operator accepts a list of integer numbers and sequentially applies each mapper to each number in the list (performs multiple transformations). The result is a list with transformed values.

Write three functions:

1. identityTransformation repeats identity transformation three times just for example.

2. multTwoAndThenAddOneTransformation multiplies by two each integer number and then add one to its.

3. squareAndThenGetNextEvenNumberTransformation quares each integer number and then calculates the next even number following it
An input list with integer numbers [1, 2, 3].
identityTransformation returns the result list [1, 2, 3].
multTwoAndThenAddOneTransformation returns the result list [3, 5, 7].
squareAndThenGetNextEvenNumberTransformation returns the result list [2, 6, 10].

```java
# 1
Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper = intUnaryOperators -> integers -> {
            List<Integer> resList = new ArrayList<>();
            for(int i : integers){
                for(IntUnaryOperator operator : intUnaryOperators){
                    i = operator.applyAsInt(i);
                }
                resList.add(i);
            }
            return resList;
        };

# 2
Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper = intUnaryOperators -> integers -> integers.stream()
                .map(x -> intUnaryOperators.stream()
                        .reduce(IntUnaryOperator::andThen)
                        .get()
                        .applyAsInt(x))
                .collect(Collectors.toList());

UnaryOperator<List<Integer>> identityTransformation = multifunctionalMapper.apply(Arrays.asList(x -> x, x -> x));

UnaryOperator<List<Integer>> multTwoAndThenAddOneTransformation =
                multifunctionalMapper.apply(Arrays.asList(x -> x * 2, x -> x + 1));

UnaryOperator<List<Integer>> squareAndThenGetNextEvenNumberTransformation =
                multifunctionalMapper.apply(Arrays.asList(x -> x * x, x -> x + 2 - (x % 2)));
```

2.35 

**reduceIntOperator** accepts an initial value (seed) and a combiner function and then returns a new function that combines all values in the given integer range (inclusively) into one integer value (it's a simple form of reduction).  

**sumOperator**: summing integer values in the given range. 

**productOperator**  : multiplying integer values in the given range.  

```java
BiFunc
tion<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator = (seed, operator) -> (left, right) -> IntStream.rangeClosed(left, right).reduce(seed, operator);

IntBinaryOperator sumOperator = reduceIntOperator.apply(0, Integer::sum);
IntBinaryOperator productOperator = reduceIntOperator.apply(1, Math::multiplyExact);
```




### monad

**TestMonad.java**

2.36 Understanding monads and related things 

- It has a function unit(or pure): takes a value and return a monad wrapping the value
- It has a function bind(or flatMap): takes a function that (the function) accepts a value and returning a monad
- Monad is a structure that puts a value in a computational context

In practice we'll often use an additional method **map** that may be defined as combination of bind and unit. 
map: takes a function that (the function) accepts a value and returns a value.

Java8中现有的monads：

- Stream

- CompletableFuture

- Optional

  



2.38.1 The method findUserByLogin(String login) that returns an optional value of type Optional<User>.

```java
public static Optional<User> findUserByLogin(String login) {
    //# 1
    Optional<User> res = Optional.empty();
	for(User user : users){
        if(login.equals(user.getLogin())) {
            res = Optional.ofNullable(user);
            break;
        }
    }
    //# 2
    Optional<User> res = users.stream().
                filter(user -> login.equals(user.getLogin())) //filter(user -> Objects.equals(user.getLogin(), login))
                .findFirst();
    return res;
}
```



2.38.2 Using the method you wrote for finding an user by their login, write a new method printBalanceIfNotEmpty(String userLogin)that prints an account balance for an existing user if `balance > 0`.

```java
public static void printBalanceIfNotEmpty(String userLogin) {
        //# 1
        findUserByLogin(userLogin).ifPresent(x -> {
            if(x.getAccount() == null)
                return;
            if(x.getAccount().getBalance() > 0)
                System.out.println(x.getLogin() + ": " + x.getAccount().getBalance());
        });

        //# 2
        findUserByLogin(userLogin)
                .map(User::getAccount)
                .map(Account::getBalance)
                .filter(balance -> balance > 0)
                .ifPresent(balance -> System.out.println(userLogin + ": " + balance));
    }
```

