package com.weiyi.transaction;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * @author weiyi
 * @describe: 此demo测试脏读，将数据库的隔离级别设置为Read Commettied 读未提交
 * 此demo修改数据库数据，并sleep十秒，然后报错回滚
 * @since 2019/8/15 - 9:50
 */
public class TransactionDemo2 {

    private static final String URL = "jdbc:mysql://localhost:3306/girls?suseUnicode=true&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "2580";

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 通过数据库练级地址和登录用户信息获取连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // 关闭事务自动提交
            connection.setAutoCommit(false);
            // 创建sql语句
            String sql = "update account set balary = 0 where id = 1";
            // 预编译sql语句
            preparedStatement = connection.prepareStatement(sql);
            // 执行接收返回结果
            int i = preparedStatement.executeUpdate();

            if (i > 0) {
                System.out.println("Update seccess " + i);
            }
            TimeUnit.SECONDS.sleep(5);
            int flag = 1 / 0;
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
                System.out.println("Program rollback");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
