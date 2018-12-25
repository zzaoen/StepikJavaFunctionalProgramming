package beans;

import java.util.List;

/**
 * @author : Bruce Zhao
 * @email : zhzh402@163.com
 * @date : 2018/12/19 19:39
 * @desc :
 */
public class Department {
    private String name;
    private String code;
    private List<Employee> employees;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

}

