import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/26 14:26
 * @desc :
 */
public class TestCurrying {

    public static void main(String[] args) {
//        cur_2_33_1();
//        cur_2_33_2();

        cur_2_34();
        return;
    }


    /**
     *
     */
    public static void cur_2_35() {

        //todo BiFunction接收三个参数，前两个输入类型，最后一个返回类型。所以reduceIntOperator.apply(0, Integer::sum)返回的是一个IntBinaryOperator，然后它再接收两个参数。
        BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator = (seed, operator) -> (left, right) -> IntStream.rangeClosed(left, right).reduce(seed, operator);

        IntBinaryOperator sumOperator = reduceIntOperator.apply(0, Integer::sum);
        IntBinaryOperator productOperator = reduceIntOperator.apply(1, Math::multiplyExact);

        int res = sumOperator.applyAsInt(1, 4);
        System.out.println(res);


    }

    /**
     * multifunctionalMapper accepts a list of operators (mappers) and returns
     * a new operator. The returned operator accepts a list of integer numbers
     * and sequentially applies each mapper to each number in the list (performs
     * multiple transformations). The result is a list with transformed values.
     * <p>
     * Write three functions:
     * 1. identityTransformation repeats identity transformation three times
     * just for example.
     * 2. multTwoAndThenAddOneTransformation multiplies by two each integer
     * number and then add one to its.
     * 3. squareAndThenGetNextEvenNumberTransformation quares each integer
     * number and then calculates the next even number following it
     * <p>
     * An input list with integer numbers [1, 2, 3].
     * identityTransformation returns the result list [1, 2, 3].
     * multTwoAndThenAddOneTransformation returns the result list [3, 5, 7].
     * squareAndThenGetNextEvenNumberTransformation returns the result list [2, 6, 10].
     */
    public static void cur_2_34() {
//        1
        /*Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper = intUnaryOperators -> integers -> {
            List<Integer> resList = new ArrayList<>();
            for(int i : integers){
                for(IntUnaryOperator operator : intUnaryOperators){
                    i = operator.applyAsInt(i);
                }
                resList.add(i);
            }
            //todo 为什么返回的是一个list? 因为UnaryOperator接收一个类型并返回同一个类型。
            return resList;
        };*/

//        2
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

        List<Integer> res = squareAndThenGetNextEvenNumberTransformation.apply(Arrays.asList(1, 2, 3));
        System.out.println(res);

    }


    /**
     *******************************************************************
     */

    /**
     * Write a curried function (using lambdas) that accepts four string
     * arguments and concatenated all in one string.
     */
    public static void cur_2_33_2() {
        Function<String, Function<String, Function<String, Function<String, String>>>> function = str1 -> str2 -> str3 -> str4 -> str1.toLowerCase() + str3.toUpperCase() + str2.toLowerCase() + str4.toUpperCase();
        String res = function.apply("aa").apply("bb").apply("cc").apply("dd");
        System.out.println(res);
    }

    /**
     * Write a curried form of the function f(x,y,z)=x+y∗y+z∗z∗z using lambda
     * expressions in Java 8 style.
     */

    //todo Function两个参数，最后一个参数是返回类型
    //BiFunction<Integer, Integer, BiFunction<Integer, Integer, Function<Integer, Integer>>> = (x1, x2) -> (x3, x4) -> x5 -> x1+x2;
    public static void cur_2_33_1() {
        Function<Integer, Function<Integer, Function<Integer, Integer>>> function = x -> y -> z -> x + y * y + z * z * z;
        Integer res = function.apply(2).apply(3).apply(4);
        System.out.println(res);
    }
}
