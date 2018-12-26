import beans.Account;
import beans.LogEntry;
import beans.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.reducing;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/26 13:41
 * @desc :
 */
public class TestCollectors {

    public static void main(String[] args) {

        return;
    }

    /**
     * Write a collector that calculates how many times was clicked
     * each url by users.
     */

    public void col_2_29() {
        LogEntry logEntry1 = new LogEntry("2017", "zz", "www.google.com");
        LogEntry logEntry2 = new LogEntry("2018", "ab", "www.baidu.com");
        LogEntry logEntry3 = new LogEntry("2018", "ab", "www.google.com");
        List<LogEntry> logs = Arrays.asList(logEntry1, logEntry2, logEntry3);

        Map<String, Long> res = logs.stream()
                .collect(Collectors.groupingBy(LogEntry::getUrl, Collectors.counting()));
    }

    /**
     * Write a collector that calculates the total sum of
     * transactions (long type, not integer) by each account (i.e.
     * by account number).
     */
    public void col_2_28() {
        Transaction transaction1 = new Transaction("123", 100, new Account("111", 1000));
        Transaction transaction2 = new Transaction("f22", 1000, new Account("112", 1000));
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

//        1
        /*Map<String, Long> res = transactions.stream()
                .collect(Collectors.groupingBy(x -> x.getAccount().getNumber(), Collectors.summingLong(Transaction::getSum)));*/

//        2
        Map<String, Long> res = transactions.stream()
                .collect(Collectors.toMap(x -> x.getAccount().getNumber(), Transaction::getSum, Long::sum));
    }


    /**
     * Write a collector that partitions all words in a stream into
     * two groups: palindromes (true) and usual words (false).
     */
    public void col_2_27() {
        String[] words = new String[]{"aaa", "aa", "a", "ba"};
//        1
        /*Map<Boolean, List<String>> res = Arrays.stream(words)
                .collect(Collectors.partitioningBy(word -> new StringBuilder(word).reverse().equals(word)));*/

//        2
        Map<Boolean, List<String>> res = Arrays.stream(words)
                .collect(Collectors.partitioningBy(word -> word.contentEquals(new StringBuffer(word).reverse())));


    }


    /**
     * Write a collector that evaluates the product of squares of
     * integer numbers in a Stream<Integer>.
     */

    public void col_2_26() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
//        1
        /*long res = numbers.stream().collect(reducing(1, x -> x * x, Math::multiplyExact));*/

//        2
        long res = numbers.stream().collect(reducing(1, (acc, x) -> acc * x * x));

        System.out.println(res);

    }
}
