package com.example.springboot_jpa;

import com.example.springboot_jpa.dao.UserDao;
import com.example.springboot_jpa.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class SpringbootJpaApplicationTests {
    @Autowired
    DataSource dataSource;

    @Autowired
    UserDao userDao;

    @Test
    void contextLoads() throws SQLException {

        System.out.println(dataSource);
        System.out.println(dataSource.getConnection());
    }

    @Test
    void find() {
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testuser() {
        //添加测试数据
        User save = userDao.save(new User("1111", "test-data-jpa", "first-pass"));
        System.out.println(save);
        userDao.save(new User("9527", "test-data-jpa", "second-pass"));
    }

}
