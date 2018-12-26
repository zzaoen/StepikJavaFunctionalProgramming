package beans;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/21 15:01
 * @desc :
 */
public class Transaction {

    private String uuid;
    private String state;
    private long sum;
    private String created;
    private Account account;

    public Transaction(String uuid, long sum, Account account) { //2.28
        this.uuid = uuid;
        this.sum = sum;
        this.account = account;
    }

    public Transaction(String number, String state, long sum, String created) {
        this.uuid = number;
        this.state = state;
        this.sum = sum;
        this.created = created;
    }

    public String getNumber() {
        return uuid;
    }

    public void setNumber(String number) {
        this.uuid = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
