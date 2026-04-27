package com.wiki.dao;

import com.wiki.entity.User;
import com.wiki.utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    /**
     * 验证登录
     */
    public User login(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                // 🚨 必须将 role 也查出来，用于判断是不是管理员
                user.setRole(rs.getInt("role"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }
    
    /**
     * 【管理员功能】查询所有用户列表
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // 不返回密码字段以保障安全，并按创建时间倒序或者 ID 倒序
            String sql = "SELECT id, username, nickname, role, create_time FROM user ORDER BY id DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setNickname(rs.getString("nickname"));
                user.setRole(rs.getInt("role"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return userList;
    }
    
    /**
     * 【管理员功能】删除（封禁）用户
     */
    public boolean deleteUser(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            // 删除指定ID的用户，注意：实际项目中可能是 "逻辑删除"（修改状态），这里先实现物理删除
            String sql = "DELETE FROM user WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }
}
