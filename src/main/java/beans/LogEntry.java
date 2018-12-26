package beans;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/21 16:38
 * @desc :
 */
public class LogEntry {
    private String created;
    private String login;
    private String url;

    public LogEntry(String created, String login, String url) {
        this.created = created;
        this.login = login;
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
