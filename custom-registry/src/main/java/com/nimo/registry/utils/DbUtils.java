package com.nimo.registry.utils;

import java.sql.*;

/**
 * 数据库工具类
 */
public class DbUtils {

    static Connection conn;
    static PreparedStatement ps;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rpc-registry","root","123456");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void read() {
        ResultSet resultSet = null;
        try {
            ps = conn.prepareStatement("select * from tb_service");
            resultSet = ps.executeQuery();
            while(resultSet.next())
                System.out.println(resultSet.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        DbUtils.read();
    }

}
