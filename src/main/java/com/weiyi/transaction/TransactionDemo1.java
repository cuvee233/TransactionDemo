package com.weiyi.transaction;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * @author weiyi
 * @describe:
 *          此demo读两次数据库，第一次读到未提交的数据，脏读
 *          demo1回滚之后再次读取数据，发生读取数据不一致的情况
 *          My name is 洋, I'm id is 1 and My balary is 0
 *          My name is 洋, I'm id is 1 and My balary is 1000
 * @since 2019/8/15 - 9:06
 */
public class TransactionDemo1 {

    private static final String URL = "jdbc:mysql://localhost:3306/girls?suseUnicode=true&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "2580";

    public static void main(String[] args) throws Exception{

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
            String sql = "select * from account";
            // 预编译sql语句
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= 2; i++) {
                // 执行接收返回结果
                ResultSet resultSet = preparedStatement.executeQuery();
                // 提交事务
                connection.commit();
                // 解析结果
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == 1) {
                        int balary = resultSet.getInt("balary");
                        String name = resultSet.getString("username");
                        System.out.println("My name is " + name + ", I'm id is " + id + " and My balary is " + balary);
                    }
                }
                TimeUnit.SECONDS.sleep(6);
            }
        } catch (Exception e) {
            connection.rollback();
        } finally {
            preparedStatement.close();
            connection.close();
        }

    }

}
