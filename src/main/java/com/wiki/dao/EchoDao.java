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
}