import beans.Account;
import javafx.collections.ListChangeListener;

import java.io.FilenameFilter;
import java.math.BigInteger;
import java.nio.file.DirectoryStream;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;



/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/24 19:24
 * @desc :
 */
public class Basic {

    public static void main(String[] args) {
//        b_2_2_1();
//        b_2_2_2();

        b_2_3();

//        b_2_4();

//        b_2_5();

//        b_2_6_1();
//        b_2_6_2();

//        b_2_9();

//        b_2_10();

//        b_2_12(Arrays.asList());

//        b_2_17(17);

//        b_2_18("bad better", Arrays.asList("good", "better", "bad"));

//        b_2_19(IntStream.of(2, 60, 90, 20), IntStream.of(3, 30, 35, 75));

//        b_2_20(5);

//        b_2_21(21, 30);

//        funny();


        return;
    }


    /**
     * 有趣的用法
     */
    public static void funny() {
        //todo 直接对输入处理
        /*Scanner scanner = new Scanner(System.in);
        Arrays.stream(scanner.nextLine().split("\\s"))
                .distinct()
                .reduce((s1, s2) -> s1 + "-" + s2)
                .ifPresent(System.out::println);*/


        //todo 逆序 2.13 Comparator
        List<String> strings = Arrays.asList("zhao", "yao", "abc", "zz");
        strings.sort(Comparator.comparing(String::trim, Comparator.reverseOrder()));
        System.out.println(strings);
    }


    /**
     * Create a parallel LongStream for filtering prime numbers in
     * the given range (inclusively).
     */
    public static LongStream b_2_31(long rangeBegin, long rangeEnd){
        LongStream res = LongStream.rangeClosed(rangeBegin, rangeEnd)
                .parallel()
                .filter(x -> (x & 1) != 0);


        return res;
    }

    /**
     * Write a method for calculating the sum of odd numbers in the
     * given interval (inclusively) using Stream API.
     */
    public static long b_2_21(long start, long end) {
//        1
        /*long res = LongStream.rangeClosed(start, end)
                .filter(x -> x % 2 == 1)
                .sum();*/

//        2
        long res = LongStream.rangeClosed(start, end)
                .filter(x -> x % 2 == 1)
                .reduce(0L, Long::sum);
        System.out.println(res);
        return res;
    }

    /**
     * Write a method to calculate a factorial value using stream.
     */
    public static long b_2_20(long n) {
//        1
        /*long res = LongStream.rangeClosed(2, n).reduce(1L, Math::multiplyExact);*/

//        2
        long res = LongStream.rangeClosed(2, n).reduce(1L, (x, y) -> x * y);

        System.out.println(res);
        return res;
    }


    /**
     * 合并stream
     * You have two IntStream. The first stream contains even numbers
     * and the second stream contains odd numbers. Create the third
     * stream that contains numbers from both streams which is
     * divisible by 3 and 5.
     */
    public static IntStream b_2_19(IntStream evenStream, IntStream oddStream) {
//        1
        /*IntStream res = IntStream.concat(evenStream, oddStream)
                .filter(x -> x % 15 == 0)
                .sorted()
                .skip(2);*/


//        2
        // TODO 这里的collect不能使用Collectors.toList()
        List<Integer> resList = evenStream.filter(x -> x % 15 == 0)
                .collect(LinkedList::new, List::add, List::addAll);
        resList.addAll(oddStream.filter(x -> x % 15 == 0)
                .collect(LinkedList::new, List::add, List::addAll));
        IntStream res = resList.stream().mapToInt(x -> x).sorted().skip(2);

        res.forEach(System.out::println);
        return res;
    }

    /**
     * Create a stream that will detect bad words in a text according to
     * a bad words list. All words in the text are divided by whitespaces
     * (always only one whitespace between two words).
     */

    public static Stream<String> b_2_18(String text, List<String> badWords) {
//        1
//        这个是通过字符串的contains方法判断是否包含list中每一个元素
        /*Stream<String> res = badWords.stream().filter(text::contains).sorted().distinct();*/

//        2
//        这个是将text拆分后与list中每一个元素比较
        /*Stream<String> res = Arrays.stream(text.split("\\s")).filter(badWords::contains).sorted().distinct();*/

//        3
        Stream<String> res = Arrays.stream(text.split("\\s")).filter(s -> badWords.stream().anyMatch(badWord -> badWord.equals(s))).sorted().distinct();

        res.forEach(System.out::println);
        return res;
    }

    /**
     * Write a method using Stream API to check the input number is prime
     * or not.
     */
    public static boolean b_2_17(final long number) {
//        1
        /*boolean res = !LongStream.range(2, number/2+1).anyMatch(x -> number % x == 0);*/

//        2
        /*boolean res = LongStream.range(2, number / 2 + 1).noneMatch(i -> number % i == 0);*/

//        3
        boolean res = new BigInteger(String.valueOf(number)).isProbablePrime(1);

        System.out.println(res);
        return res;
    }


    /**
     * Composing 2.11概念
     * Write the disjunctAll method that accepts a list of IntPredicate's
     * and returns a single IntPredicate. The result predicate is a
     * disjunction of all input predicates.
     * 这题disjunction是关键
     * 0 or 0 = 0， 其他情况都是1
     */
    public static IntPredicate b_2_12(List<IntPredicate> predicates) {
//        1
        /*IntPredicate resPredicate = predicates.stream().reduce(x -> false, IntPredicate::or);*/
//        2
        /*IntPredicate resPredicate = predicates.stream().reduce(IntPredicate::or).orElse(x -> false);*/
//        3
        IntPredicate resPredicate = x -> false;
        predicates.forEach(predicate -> resPredicate.or(predicate));
        return resPredicate;
    }

    //todo 有关位运算的使用 IntPredicate::or ::and

    /**
     * Write your own functional interface (TernaryIntPredicate) and use it with a lambda expression.
     * The interface must have a single non-static (and non-default) method test
     * with three int arguments that returns boolean value.
     * The lambda expression has to return true if all passed values are different
     * otherwise false.
     */
    public static void b_2_10() {
//        1
        /*TernaryIntPredicate allValuesAreDifferentPredicate = (x, y, z) -> {
            if(x == y && x == z)
                return true;
            else
                return false;
        };*/

        TernaryIntPredicate allValuesAreDifferentPredicate = (x, y, z) -> x != y && y != z && x != z;

        boolean res = allValuesAreDifferentPredicate.test(1, 1, 1);
        System.out.println(res);

    }


    /**
     * Behaviour parametrization with lambda expressions
     * 1. get list with all non-empty accounts (balance > 0) and save it to the
     * variable nonEmptyAccounts
     * 2. get all non-locked accounts with too much money (balance >= 100 000 000)
     * and save it to the variable accountsWithTooMuchMoney
     */
    public static void b_2_9() {
        Account account1 = new Account("111", 100000001, false);
        Account account2 = new Account("222", 0, false);
        Account account3 = new Account("333", 1000, false);
        List<Account> accounts = Arrays.asList(account1, account2, account3);

//        1
        /*List<Account> balanceOverZeroList = accounts.stream()
                .filter(account -> account.getBalance() > 0)
                .collect(Collectors.toList());
        List<Account> list = accounts.stream()
                .filter(account -> (account.getBalance() >= 100000000 && !account.isLocked()))
                .collect(Collectors.toList());*/

//        2
        Predicate<Account> balanceOverZeroPred = (Account account) -> account.getBalance() > 0;
        Predicate<Account> balanceTooMuchPred = (Account account) -> account.getBalance() > 100000000;
        Predicate<Account> isLockedPred = (Account account) -> !account.isLocked();
        List<Account> balanceOverZeroList = accounts.stream()
                .filter(balanceOverZeroPred).collect(Collectors.toList());
        List<Account> list = accounts.stream()
                .filter(balanceTooMuchPred.and(isLockedPred))
                .collect(Collectors.toList());


        System.out.println(balanceOverZeroList);
        System.out.println(list);

    }


    /**
     * Using closure write a lambda expression that adds prefix (before) and
     * suffix (after) to its single string argument; prefix and suffix are final
     * variables and will be available in the context during testing.
     */
    public static void b_2_6_2() {
        final String prefix = "pre-", suffix = "-suf";
//        1
        /*Function<String, String> stringFunction = s -> prefix + s.trim() + suffix;*/

//        2
        Function<String, String> stringFunction = s -> String.join("", prefix, s.trim(), suffix);
        String resStr = stringFunction.apply("mid");
        System.out.println(resStr);


    }
//todo 没有StringOperator这种类，需要接受一个String，处理完并返回一个String。这个时候需要使用Function<String, String>

    /**
     * 闭包
     * Using closure write a lambda expression that calculates a∗x2+b∗x+c
     * where a, b, c are context final variables. Note, the result is double
     */
    public static void b_2_6_1() {
        final int a = 1, b = 2, c = 3;
        DoubleUnaryOperator operator = x -> a * x * x + b * x + c;
        double res = operator.applyAsDouble(1);
        System.out.println(res);
    }
//todo operator是接收一个或者两个，然后有返回值; Function也是接收并返回值


    /**
     * Write a lambda expression that accepts a list of strings and returns
     * new list of distinct strings (without repeating). The order of
     * elements in the result list may be any (elements will be sorted by
     * the testing system).
     */
    public static void b_2_5() {
//        1
        /*Function<List<String>, List<String>> listConsumer = strings -> strings.stream().distinct().collect(Collectors.toList());*/

//        2
        Function<List<String>, List<String>> listFunction = strings -> new ArrayList(new HashSet(strings));

        List<String> resList = listFunction.apply(Arrays.asList("java", "scala", "java", "kotlin"));
        System.out.println(resList);

    }

    /**
     * Write a lambda expression that accepts two long arguments as a range
     * borders and calculates (returns) production of all numbers in this
     * range (inclusively).
     */
    public static void b_2_4() {
//        1
        /*LongBinaryOperator operator = (x, y) -> LongStream.rangeClosed(x, y)
                .reduce(1L, Math::multiplyExact);*/

//        2
        /*LongBinaryOperator operator = (x, y) -> LongStream.rangeClosed(x, y)
                .reduce(1L, (acc, temp) -> acc * temp);*/


//        3
        LongBinaryOperator operator = (x, y) -> {
            long res = 1;
            while (x < y)
                res *= y--;
            return res;
        };
        long res = operator.applyAsLong(1, 4);
        System.out.println(res);
    }


    /**
     * Write a lambda expression that accepts seven (!) string arguments and returns a string
     * in upper case concatenated from all of them (in the order of arguments).
     */

    public static void b_2_3() {


//        SevenArgsFunction test_b_2_3 = (String a, String b, String c, String d, String e, String f, String g) -> (a + b + c + d + e + f + g).toUpperCase();
        SevenArgsFunction<String, String, String, String, String, String, String, String> function = (a, b, c, d, e, f, g) -> (a + b + c + d + e + f + g).toUpperCase();
        String res = function.apply("a", "b", "c", "d", "e", "f", "g");
        System.out.println(res);

//        1
        /*(a, b, c, d, e, f, g) -> (a + b + c + d + e + f + g).toUpperCase();*/

//        2
        /*(a, b, c, d, e, f, g) -> Stream.of(a, b, c, d, e, f, g).reduce("", String::concat).toUpperCase();*/

//        3

    }


    /**
     * Write a lambda expression that accepts a long value and returns a next even number.
     */
    public static void b_2_2_2() {
//        1
        /*IntUnaryOperator operator = x -> {
            while(++x % 2 != 0);
            return x;
        };*/



//        2
        /*IntUnaryOperator operator = x -> x + 2 - (x % 2);*/

//        3
        IntUnaryOperator operator = x -> (x | 1) + 1;
        int res = operator.applyAsInt(2);
        System.out.println(res);
    }

    /**
     * Write a lambda expression that accepts two integers arguments and returns max of them.
     */
    public static void b_2_2_1() {
//        1
        /*IntBinaryOperator operator = (x, y) -> x > y ? x : y;*/
//        2
        IntBinaryOperator operator = Math::max;

        int res = operator.applyAsInt(3, 4);
        System.out.println(res);
    }


}

@FunctionalInterface
interface SevenArgsFunction<A, B, C, D, E, F, G, R>{
    R apply(A a, B b, C c, D d, E e, F f, G g);
}

@FunctionalInterface
interface TernaryIntPredicate {
    boolean test(int arg1, int arg2, int arg3);
}