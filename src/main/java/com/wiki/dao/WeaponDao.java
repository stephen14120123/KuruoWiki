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
}