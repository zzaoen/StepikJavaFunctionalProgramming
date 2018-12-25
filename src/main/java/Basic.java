import beans.Account;
import javafx.collections.ListChangeListener;

import java.io.FilenameFilter;
import java.nio.file.DirectoryStream;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@FunctionalInterface
interface TernaryIntPredicate{
    boolean test(int arg1, int arg2, int arg3);
}

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

//        b_2_4();

//        b_2_5();

//        b_2_6_1();
//        b_2_6_2();

//        b_2_9();

//        b_2_10();

//        b_2_12(Arrays.asList());

        funny();

        return;
    }


    /**
     * 有趣的用法
     */
    public static void funny() {
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

    public void test(){

    }


    /**
     * Composing 2.11概念
     * Write the disjunctAll method that accepts a list of IntPredicate's
     * and returns a single IntPredicate. The result predicate is a
     * disjunction of all input predicates.
     * 这题disjunction是关键
     * 0 or 0 = 0， 其他情况都是1
     */
    public static IntPredicate b_2_12(List<IntPredicate> predicates){
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
     * You need to write your own functional interface (TernaryIntPredicate) and
     * use it with a lambda expression.
     * The interface must have a single non-static (and non-default) method test
     * with three int arguments that returns boolean value.
     * The lambda expression has to return true if all passed values are different
     * otherwise false.
     */
    public static void b_2_10(){
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
    public static void b_2_9(){
        Account account1 = new Account("111", 100000001, false);
        Account account2 = new Account("222", 0, false);
        Account account3 = new Account("333", 1000, false);
        List<Account> accounts = Arrays.asList(account1, account2, account3);

//        1
        /*List<Account> balanceOverZeroList = accounts.stream().filter(account -> account.getBalance() > 0).collect(Collectors.toList());
        List<Account> list = accounts.stream().filter(account -> (account.getBalance() >= 100000000 && !account.isLocked())).collect(Collectors.toList());*/

//        2
        Predicate<Account> balanceOverZeroPred = (Account account) -> account.getBalance() > 0;
        Predicate<Account> balanceTooMuchPred = (Account account) -> account.getBalance() > 100000000;
        Predicate<Account> isLockedPred = (Account account) -> !account.isLocked();
        List<Account> balanceOverZeroList = accounts.stream().filter(balanceOverZeroPred).collect(Collectors.toList());
        List<Account> list = accounts.stream().filter(balanceTooMuchPred.and(isLockedPred)).collect(Collectors.toList());


        System.out.println(balanceOverZeroList);
        System.out.println(list);

    }


    /**
     * Using closure write a lambda expression that adds prefix (before) and
     * suffix (after) to its single string argument; prefix and suffix are final
     * variables and will be available in the context during testing.
     */
    public static void b_2_6_2(){
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
//todo operator是接收一个或者两个，然后有返回值


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
     * range (inclusively)
     */
    public static void b_2_4() {
//        1
        /*LongBinaryOperator operator = (x, y) -> LongStream.rangeClosed(x, y).reduce(1L, Math::multiplyExact);*/

//        2
        /*LongBinaryOperator operator = (x, y) -> LongStream.rangeClosed(x, y).reduce(1L, (acc, temp) -> acc * temp);*/

//        3
        LongBinaryOperator operator = (x, y) -> {
            int res = 1;
            while (x < y)
                res *= y--;
            return res;
        };
        long res = operator.applyAsLong(1, 4);
        System.out.println(res);
    }


    /**
     * Write a lambda expression that accepts a long value and returns a next even number.
     */
    public static void b_2_3() {

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
