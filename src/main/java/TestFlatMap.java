import beans.Account;
import beans.Department;
import beans.Employee;
import beans.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/26 12:18
 * @desc :
 */
public class TestFlatMap {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        //1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5
        numbers.stream()
                .flatMap(x -> Stream.generate(() -> x).limit(x))
                .collect(Collectors.toList());

        //1, 1, 2, 1, 2, 3, 1, 2, 3, 4, 1, 2, 3, 4, 5
        numbers.stream()
                .flatMapToInt(x -> IntStream.rangeClosed(1, x))
                .boxed()
                .collect(Collectors.toList());

        //1,  2, 3,  3, 4, 5,  4, 5, 6, 7,  5, 6, 7, 8, 9
        numbers.stream()
                .flatMapToInt(x -> IntStream.iterate(x, val -> val + 1))
                .boxed()
                .collect(Collectors.toList());


        return;
    }

    /**
     * Write a method using Stream API that calculates the total
     * sum of canceled transactions for all non-empty accounts
     * (balance > 0).
     */
    public static long flapMap_2_24(List<Account> accounts) {
//        1
        /*long res = accounts.stream()
                .filter(account -> account.getBalance() > 0)
                .flatMap(account -> account.getTransactions().stream())
                .filter(transaction -> transaction.getState().equals("CANCELED"))
                .mapToLong(Transaction::getSum)
                .sum();
        System.out.println(res);*/

//        2
        /*long res = accounts.stream()
                .filter(account -> account.getBalance() > 0)
                .flatMap(account -> account.getTransactions().stream())
                .filter(transaction -> transaction.getState().equals("CANCELED"))
                .map(Transaction::getSum)
                .reduce(0L, Long::sum);*/

//        3
        //todo flatMapToLong
        long res = accounts.stream()
                .filter(account -> account.getBalance() > 0)
                .flatMapToLong(accout -> accout.getTransactions()
                        .stream()
                        .filter(transaction -> transaction
                                .getState().equals("CANCELED"))
                        .mapToLong(Transaction::getSum))
                .sum();


        System.out.println(res);

        return res;
    }

    /**
     * Calculates the number of employees with salary >= threshold
     * (only for 111- departments)
     */

    public static long flapMap_2_23(List<Department> departments, long threshold) {
//        1
        /*long res = departments.stream()
                .filter(department -> department.getCode().startsWith("111-"))
                .flatMap(department -> department.getEmployees().stream())
                .filter(employee -> employee.getSalary() >= threshold)
                .count();*/

//        2
        /*long res = departments.stream()
                .filter(department -> department.getCode().startsWith("111-"))
                .flatMapToLong(department -> department.getEmployees().stream().mapToLong(Employee::getSalary))
                .filter(salary -> salary >= threshold)
                .count();*/
        //todo mapToLong either than flatMapToLong

//        3
        long res = departments.stream()
                .filter(department -> department.getCode().startsWith("111-"))
                .map(Department::getEmployees)
                .flatMap(list -> list.stream()) //.flatMap(List::stream)
                .map(Employee::getSalary)
                .filter(salary -> salary >= threshold)
                .count();

        System.out.println(res);
        return res;
    }

}
