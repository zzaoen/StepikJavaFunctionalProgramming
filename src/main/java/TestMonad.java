import beans.Account;
import beans.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/26 15:49
 * @desc :
 */
public class TestMonad {

    public static void main(String[] args) {

        return;
    }

    public static Set<User> users = new HashSet<>();

    /**
     * 2.38.2
     * Using the method you wrote for finding an user by their login, write a new
     * method printBalanceIfNotEmpty(String userLogin)that prints an account
     * balance for an existing user if `balance > 0`.
     */
    public static void printBalanceIfNotEmpty(String userLogin) {
//        1
        /*findUserByLogin(userLogin).ifPresent(x -> {
            if(x.getAccount() == null)
                return;
            if(x.getAccount().getBalance() > 0)
                System.out.println(x.getLogin() + ": " + x.getAccount().getBalance());
        });*/

//        2
        findUserByLogin(userLogin)
                .map(User::getAccount)
                .map(Account::getBalance)
                .filter(balance -> balance > 0)
                .ifPresent(balance -> System.out.println(userLogin + ": " + balance));
    }


    /**
     * 2.38.1
     * The method findUserByLogin(String login) that returns an optional value of type Optional<User>. I
     */
    public static Optional<User> findUserByLogin(String login) {

//        1
        /*Optional<User> res = Optional.empty();
        for(User user : users){
            if(login.equals(user.getLogin())) {
                res = Optional.ofNullable(user);
                break;
            }
        }*/

//        2
        Optional<User> res = users.stream().
                filter(user -> login.equals(user.getLogin())) //filter(user -> Objects.equals(user.getLogin(), login))
                .findFirst();

        return res;
    }
}
