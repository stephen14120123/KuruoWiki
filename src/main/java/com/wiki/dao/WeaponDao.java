package com.wiki.dao;

import com.wiki.entity.WeaponInfo;
import com.wiki.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WeaponDao {
    public List<WeaponInfo> getAllWeapons() {
        List<WeaponInfo> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM weapon_info ORDER BY rarity DESC, id ASC"; // 按星级从高到低排
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                WeaponInfo w = new WeaponInfo();
                w.setId(rs.getInt("id"));
                w.setName(rs.getString("name"));
                w.setRarity(rs.getInt("rarity"));
                w.setWeaponType(rs.getString("weapon_type"));
                w.setBaseAtk(rs.getInt("base_atk"));
                w.setSubStatType(rs.getString("sub_stat_type"));
                w.setSubStatValue(rs.getString("sub_stat_value"));
                w.setSkillName(rs.getString("skill_name"));
                w.setSkillDesc(rs.getString("skill_desc"));
                w.setImageUrl(rs.getString("image_url"));
                list.add(w);
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

    public boolean addWeapon(WeaponInfo w) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO weapon_info (name, rarity, weapon_type, base_atk, sub_stat_type, sub_stat_value, skill_name, skill_desc, image_url) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, w.getName());
            pstmt.setInt(2, w.getRarity());
            pstmt.setString(3, w.getWeaponType());
            pstmt.setInt(4, w.getBaseAtk() != null ? w.getBaseAtk() : 0);
            pstmt.setString(5, w.getSubStatType());
            pstmt.setString(6, w.getSubStatValue());
            pstmt.setString(7, w.getSkillName());
            pstmt.setString(8, w.getSkillDesc());
            pstmt.setString(9, w.getImageUrl());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    public boolean updateWeapon(WeaponInfo w) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE weapon_info SET name=?, rarity=?, weapon_type=?, base_atk=?, sub_stat_type=?, sub_stat_value=?, skill_name=?, skill_desc=?, image_url=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, w.getName());
            pstmt.setInt(2, w.getRarity());
            pstmt.setString(3, w.getWeaponType());
            pstmt.setInt(4, w.getBaseAtk() != null ? w.getBaseAtk() : 0);
            pstmt.setString(5, w.getSubStatType());
            pstmt.setString(6, w.getSubStatValue());
            pstmt.setString(7, w.getSkillName());
            pstmt.setString(8, w.getSkillDesc());
            pstmt.setString(9, w.getImageUrl());
            pstmt.setInt(10, w.getId());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    public boolean deleteWeapon(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM weapon_info WHERE id = ?";
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