package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.util.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static UserServiceImpl service = new UserServiceImpl();
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        service.createUsersTable();
        service.saveUser("Ivan", "Pomarkov",  (byte) 20);
        service.saveUser("Petr", "Pomarkov", (byte) 15);
        service.saveUser("Sergey", "Pomarkov", (byte) 13);
        service.saveUser("Egor", "Pomarkov", (byte) 14);
        users = service.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
