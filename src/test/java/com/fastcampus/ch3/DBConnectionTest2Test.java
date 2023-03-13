package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import javax.swing.tree.ExpandVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired
    DataSource ds;


    @Test
    public void springJdbcConnectTest() throws Exception {
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn != null);   // 괄호 안의 조건식이 true면 테스트 성공 아니면 실패
    }

    @Test
    public void insertUserTest() throws Exception {
        User user = new User("asdf2", "1234", "abc", "aaa@aaa.com", new Date(), "instar", new Date());
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt == 1);
    }

    @Test
    public void selectUserTest() throws Exception {
        deleteAll();
        User user = new User("asdf2", "1234", "abc", "aaa@aaa.com", new Date(), "instar", new Date());
        int rowCnt = insertUser(user);
        User user2 = selectUser("asdf2");

        assertTrue(user2.getId().equals("asdf2"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        deleteAll();
        int rowCnt = deleteUser("asdf2");

        assertTrue(rowCnt == 0);

        User user = new User("asdf2", "1234", "abc", "aaa@aaa.com", new Date(), "instar", new Date());
        rowCnt = insertUser(user);

        assertTrue(rowCnt == 1);

        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt == 1);

        assertTrue(selectUser(user.getId()) == null);
    }

    @Test
    public void updateUserTest() throws Exception {
        deleteAll();

        User user = new User("asdf2", "1234", "abc", "aaa@aaa.com", new Date(), "instar", new Date());
        int rowCnt = insertUser(user);

        assertTrue(rowCnt == 1);

        user.setPwd("4321");
        user.setName("park");
        user.setEmail("bbb@bbb.com");
        user.setSns("facebook");
        rowCnt = updateUser(user.getId(), user);

        assertTrue(rowCnt == 1);
    }

    // User Insert
    public int insertUser(User user) throws Exception {
        Connection connection = ds.getConnection();

//        insert into user_info (id, pwd, name, email, birth, sns, reg_date)
//        values ('asdf2', '1234', 'smith', 'aaaa@aaaa.com', '2021-01-01', 'facebook', now());

        String sql = "insert into user_info values (?, ?, ?, ?, ?, ?, now())";

        // 기존 Statement 보다 향상된 점 : SQL Injection공격, 성능향상(캐싱 효과가 있음)
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
//        pstmt.setDate( 5, (java.sql.Date)new Date(user.getBirth().getTime()));
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());
        System.out.println("pstmt = " + pstmt);
        System.out.println("user = " + user);

        int rowCnt = pstmt.executeUpdate(); // insert, delete, update 일때 씀

        return rowCnt;
    }

    // User all delete
    public void deleteAll() throws Exception {
        Connection connection = ds.getConnection();

        String sql = "delete from user_info";

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.executeUpdate();
    }

    // User select
    public User selectUser(String id) throws SQLException {
        Connection connection = ds.getConnection();

        String sql = "select * from user_info where id = ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new java.sql.Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new java.sql.Date(rs.getTimestamp(7).getTime()));

            return user;
        }

        return null;
    }

    // User delete
    public int deleteUser(String id) throws Exception {
        Connection connection = ds.getConnection();

        String sql = "delete from user_info where id = ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);

        return pstmt.executeUpdate();
    }

    // User Update
    public int updateUser(String id, User user) throws Exception {
        Connection connection = ds.getConnection();

        if (!selectUser(id).getId().equals(user.getId())) {
            return 0;
        }

        String sql = "update user_info set pwd = ?, name = ?, email = ?, sns = ?, reg_date = now() where id = ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, user.getPwd());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getSns());
        pstmt.setString(5, user.getId());

        return pstmt.executeUpdate();
    }

}