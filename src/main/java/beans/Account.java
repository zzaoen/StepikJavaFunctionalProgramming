package beans;

import java.util.List;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/13 16:40
 * @desc :
 */
public class Account {

    private String number;
    private long balance;
    private boolean isLocked; //2.9

    private List<Transaction> transactions; //2.24

    private String guid;


    public Account(long balance, String guid) { //2.38
        this.balance = balance;
        this.guid = guid;
    }

    public Account(String number, long balance) { //2.28
        this.number = number;
        this.balance = balance;
    }

    public Account(String number, long balance, boolean isLocked) { //2.9
        this.number = number;
        this.balance = balance;
        this.isLocked = isLocked;
    }


    public Account(String number, long balance, List<Transaction> transactions) { //2.24
        this.number = number;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Account(String number, long balance, boolean isLocked, List<Transaction> transactions) {
        this.number = number;
        this.balance = balance;
        this.isLocked = isLocked;
        this.transactions = transactions;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                ", balance=" + balance +
                ", isLocked=" + isLocked +
                '}';
    }
}
