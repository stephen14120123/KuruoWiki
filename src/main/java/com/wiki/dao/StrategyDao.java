package com.wiki.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import com.wiki.entity.StrategyGuide;
import com.wiki.utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StrategyDao {



    /**
     * 根据用户 ID 查询他发布的所有攻略（带上对应的角色名）
     */
    public List<StrategyGuide> getStrategiesByUserId(int userId) {
        List<StrategyGuide> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // 多表联查：查攻略表(s)的同时，把角色表(c)里的名字也带上
            String sql = "SELECT s.*, c.name AS character_name " +
                    "FROM strategy_guide s " +
                    "JOIN character_info c ON s.character_id = c.id " +
                    "WHERE s.user_id = ? ORDER BY s.create_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                StrategyGuide guide = new StrategyGuide();
                guide.setId(rs.getInt("id"));
                guide.setCharacterId(rs.getInt("character_id"));
                guide.setUserId(rs.getInt("user_id"));
                guide.setTitle(rs.getString("title"));
                guide.setContent(rs.getString("content"));
                guide.setViews(rs.getInt("views"));
                guide.setCreateTime(rs.getTimestamp("create_time"));
                // 重点：把查出来的角色名塞进去
                guide.setCharacterName(rs.getString("character_name"));
                list.add(guide);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }


    // ... 在 StrategyDao 类中添加 ...

    /**
     * 根据 ID 彻底删除一条攻略记录
     */
    /**
     * 🛡️ 安全删除：根据 帖子ID 和 登录人ID 同时匹配才能删除
     */
    public boolean deleteStrategy(int strategyId, int currentUserId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            // 🚨 核心防线：必须满足 id 匹配 且 user_id 匹配！
            String sql = "DELETE FROM strategy_guide WHERE id = ? AND user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, strategyId);
            pstmt.setInt(2, currentUserId); // 把当前登录人的 ID 传进去

            // 如果执行结果 > 0，说明不仅找到了帖子，而且发帖人就是当前登录的人
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }
    
    /**
     * 【管理员专用】强制删除任意帖子，不需要匹配 user_id
     */
    public boolean adminDeleteStrategy(int strategyId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM strategy_guide WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, strategyId);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    /**
     * 保存一条新的攻略/评论
     */
    public boolean addStrategy(StrategyGuide guide) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO strategy_guide (user_id, character_id, title, content) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, guide.getUserId()); // 🚨 动态获取 ID
            pstmt.setInt(2, guide.getCharacterId());
            pstmt.setString(3, guide.getTitle());
            pstmt.setString(4, guide.getContent());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    } // <--- 关键点：这个括号是用来结束 addStrategy 方法的

    /**
     * 根据角色ID查询所有攻略
     */
    public List<StrategyGuide> getStrategiesByCharId(int charId) {
        List<StrategyGuide> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT s.*, u.nickname FROM strategy_guide s " +
                    "JOIN user u ON s.user_id = u.id " +
                    "WHERE s.character_id = ? ORDER BY s.create_time DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, charId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                StrategyGuide guide = new StrategyGuide();
                guide.setId(rs.getInt("id"));
                guide.setTitle(rs.getString("title"));
                guide.setContent(rs.getString("content"));
                guide.setCreateTime(rs.getTimestamp("create_time"));
                guide.setAuthorName(rs.getString("nickname"));
                list.add(guide);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
    
    /**
     * 【管理员功能】查询所有的攻略列表
     */
    public List<StrategyGuide> getAllStrategies() {
        List<StrategyGuide> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // 三表联查：查询攻略、用户昵称、角色名称
            String sql = "SELECT s.*, u.nickname as author_name, c.name as character_name " +
                         "FROM strategy_guide s " +
                         "LEFT JOIN user u ON s.user_id = u.id " +
                         "LEFT JOIN character_info c ON s.character_id = c.id " +
                         "ORDER BY s.id DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                StrategyGuide guide = new StrategyGuide();
                guide.setId(rs.getInt("id"));
                guide.setCharacterId(rs.getInt("character_id"));
                guide.setUserId(rs.getInt("user_id"));
                guide.setTitle(rs.getString("title"));
                guide.setContent(rs.getString("content"));
                guide.setViews(rs.getInt("views"));
                guide.setCreateTime(rs.getTimestamp("create_time"));
                guide.setAuthorName(rs.getString("author_name"));
                guide.setCharacterName(rs.getString("character_name"));
                
                list.add(guide);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}
