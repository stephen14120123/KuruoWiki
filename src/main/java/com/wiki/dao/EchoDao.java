package com.wiki.dao;

import com.wiki.entity.EchoInfo;
import com.wiki.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EchoDao {
    public List<EchoInfo> getAllEchoes() {
        List<EchoInfo> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            // 按照 COST 值从大到小排序 (4 -> 3 -> 1)
            String sql = "SELECT * FROM echo_info ORDER BY cost DESC, id ASC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                EchoInfo echo = new EchoInfo();
                echo.setId(rs.getInt("id"));
                echo.setName(rs.getString("name"));
                echo.setCost(rs.getInt("cost"));
                echo.setSonataEffect(rs.getString("sonata_effect"));
                echo.setSkillDesc(rs.getString("skill_desc"));
                echo.setImageUrl(rs.getString("image_url"));
                list.add(echo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // ==========================================
    // 新增：后台管理员增删改查方法
    // ==========================================

    public boolean addEcho(EchoInfo e) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO echo_info (name, cost, sonata_effect, skill_desc, image_url) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, e.getName());
            pstmt.setInt(2, e.getCost() != null ? e.getCost() : 1);
            pstmt.setString(3, e.getSonataEffect());
            pstmt.setString(4, e.getSkillDesc());
            pstmt.setString(5, e.getImageUrl());

            return pstmt.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    public boolean updateEcho(EchoInfo e) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE echo_info SET name=?, cost=?, sonata_effect=?, skill_desc=?, image_url=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, e.getName());
            pstmt.setInt(2, e.getCost() != null ? e.getCost() : 1);
            pstmt.setString(3, e.getSonataEffect());
            pstmt.setString(4, e.getSkillDesc());
            pstmt.setString(5, e.getImageUrl());
            pstmt.setInt(6, e.getId());

            return pstmt.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    public boolean deleteEcho(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM echo_info WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }
}