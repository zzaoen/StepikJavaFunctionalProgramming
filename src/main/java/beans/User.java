package beans;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/23 11:36
 * @desc :
 */
public class User {

    private String login;
    private Account account;

    public User(String login, Account account) {
        this.login = login;
        this.account = account;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
