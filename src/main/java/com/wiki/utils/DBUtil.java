package com.wiki.utils;

import java.sql.*;

public class DBUtil {
    // 1. 数据库连接的 URL (注意：这里的 acg_community_db 是你之前建的数据库名)
    // 加了 &allowPublicKeyRetrieval=true 魔法咒语
    private static final String URL = "jdbc:mysql://localhost:3306/acg_community_db?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";

    // 2. 你的 MySQL 账号和密码 (🚨 这里需要改成你自己的！)
    private static final String USER = "root";
    private static final String PASSWORD = "123456"; // 如果你的密码是 123456，就改成 123456

    // 3. 静态代码块：随着类的加载，只执行一次，用来加载 MySQL 驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("🚨 数据库驱动加载失败，请检查 pom.xml 是否引入了 mysql-connector-java！");
        }
    }

    /**
     * 获取数据库连接的方法
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 释放资源的方法 (非常重要，用完数据库一定要关门)
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // 下面是一个测试方法，你可以直接在这个类里运行它！
    // ==========================================
    public static void main(String[] args) {
        try {
            Connection connection = DBUtil.getConnection();
            if (connection != null) {
                System.out.println("🎉 恭喜你！Java 成功连接到 MySQL 数据库 [acg_community_db]！");
                connection.close(); // 测试完顺手关掉
            }
        } catch (SQLException e) {
            System.out.println("❌ 连接失败！请检查：1. 数据库软件有没有启动？2. 账号密码对不对？3. 数据库名字建了没？");
            e.printStackTrace();
        }
    }
}